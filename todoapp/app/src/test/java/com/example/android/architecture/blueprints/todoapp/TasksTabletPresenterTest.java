/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailPresenter;
import com.example.android.architecture.blueprints.todoapp.tasks.TasksPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the implementation of {@link TasksTabletPresenter}
 */
public class TasksTabletPresenterTest {

    @Mock
    private TasksRepository mTasksRepository;

    @Mock
    private TasksPresenter mTasksPresenter;

    @Mock
    private TaskDetailPresenter mTaskDetailPresenter;

    @Mock
    private TasksTabletNavigator mTasksNavigator;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<TasksDataSource.GetTaskCallback> mGetTaskCallbackArgumentCaptor;

    // SUT
    private TasksTabletPresenter mTasksTabletPresenter;


    @Before
    public void setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        mTasksTabletPresenter = new TasksTabletPresenter(
                mTasksRepository, mTasksNavigator, mTasksPresenter);

        mTasksTabletPresenter.setTaskDetailPresenter(mTaskDetailPresenter);
    }

    @Test
    public void activatingTask_RefreshesList() {
        mTasksTabletPresenter.activateTask();

        verify(mTasksPresenter).startTasksPresenter();
    }

    @Test
    public void completingTask_RefreshesList() {
        mTasksTabletPresenter.completeTask();

        verify(mTasksPresenter).startTasksPresenter();
    }

    @Test
    public void editingTask_RefreshesList() {
        mTasksTabletPresenter.editTask();

        verify(mTasksPresenter).startTasksPresenter();
    }

    @Test
    public void clearCompletedTasksWithNoDetail() {
        mTaskDetailPresenter = null;

        mTasksTabletPresenter.clearCompletedTasks();
        verify(mTasksNavigator, never()).removeDetailPane();
    }

    @Test
    public void clearCompletedTask_CompleteTaskOpen() {
        // If after the completed tasks are cleared and the task no longer exist, make sure the
        // detail pane is closed.
        Task task = new Task("Title1", "Description1");
        when(mTaskDetailPresenter.getTaskId()).thenReturn(task.getId());

        mTasksTabletPresenter.clearCompletedTasks();

        verify(mTasksRepository).getTask(
                eq(task.getId()), mGetTaskCallbackArgumentCaptor.capture());
        mGetTaskCallbackArgumentCaptor.getValue().onDataNotAvailable();

        verify(mTasksNavigator).removeDetailPane();
    }

    @Test
    public void clearCompletedTask_ActiveTaskOpen() {
        // If after the completed tasks are cleared but the task still exist, make sure the
        // detail pane is NOT closed.
        Task task = new Task("Title1", "Description1");
        when(mTaskDetailPresenter.getTaskId()).thenReturn(task.getId());

        mTasksTabletPresenter.clearCompletedTasks();

        verify(mTasksRepository).getTask(
                eq(task.getId()), mGetTaskCallbackArgumentCaptor.capture());
        mGetTaskCallbackArgumentCaptor.getValue().onTaskLoaded(new Task("Othertask", "OtherDesc"));

        verify(mTasksNavigator, never()).removeDetailPane();
    }

    @Test
    public void deleteTask_removesPaneAndDetailPresenter() {
        Task task = new Task("Title1", "Description1");
        when(mTaskDetailPresenter.getTaskId()).thenReturn(task.getId());

        mTasksTabletPresenter.deleteTask();

        verify(mTasksNavigator).removeDetailPane();
        verify(mTasksPresenter).startTasksPresenter();
        assertThat(mTasksTabletPresenter.getTaskDetailPresenter(), is(nullValue()));
    }
}
