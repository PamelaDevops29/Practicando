# Android Architecture Blueprints

<img src="https://github.com/googlesamples/android-architecture/wiki/images/aab-logo.png" alt="Android Architecture Blueprints"/>

The Android framework provides a lot of flexibility in deciding how to organize and <em>architect</em> an Android app. While this freedom is very valuable, it can also lead to apps with large classes, inconsistent naming schemes, as well as mismatching or missing architectures. These types of issues can make testing, maintaining and extending your apps difficult.

Android Architecture Blueprints is designed to demonstrate strategies to help solve or avoid these common problems. This project implements the same app using a number of different architectural concepts and tools.

You can use these samples as a learning reference, or as a starting point for creating your own apps. The focus of this project is on demonstrating how to structure your code, design your architecture, and the eventual impact of adopting these patterns on testing and maintaining your app. Note that there are many different ways to build apps using these architectures and tools. Your own particular priorities will impact how you choose to implement the concepts in these projects, so you should not consider these samples to be canonical examples. Also note that the UI of the sample app is deliberately kept simple.

## Explore the samples

Each sample app is hosted in it’s own repository branch. For more information, review each project's respective `README.md` file.

### Stable samples
| Project | Description |
| ------------- | ------------- |
| [todo-mvp](https://github.com/googlesamples/android-architecture/tree/todo-mvp/) | This version demonstrates a basic [Model-View-Presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) (MVP) architecture and provides a foundation on which the other samples are built. This sample also acts as a reference point for comparing and contrasting the other samples in this project. |
| [todo-mvp-loaders](https://github.com/googlesamples/android-architecture/tree/todo-mvp-loaders/) | This version fetches data using the [Loaders API](https://developer.android.com/guide/components/loaders.html). |
| [todo-databinding](https://github.com/googlesamples/android-architecture/tree/todo-databinding/) | This version uses the [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/index.html). |
| [todo-mvp-clean](https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/) | This version uses concepts from [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html). |
| [todo-mvp-dagger](https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/) | This version uses [Dagger2](https://google.github.io/dagger/) to add support for [dependency injection](https://en.wikipedia.org/wiki/Dependency_injection). |
[todo-mvp-contentproviders](https://github.com/googlesamples/android-architecture/tree/todo-mvp-contentproviders/) | This version fetches data using the Loaders API, and also makes use of [content providers](https://developer.android.com/guide/topics/providers/content-providers.html). Note that this version is based on the TODO-MVP-Loaders sample. |
| [todo-mvp-rxjava](https://github.com/googlesamples/android-architecture/tree/todo-mvp-rxjava/) | This version uses [RxJava](https://github.com/ReactiveX/RxJava) to implement concurrency, and abstract the data layer. |


### Samples in progress

| Project | Description |
| ------------- | ------------- |
| [dev-todo-mvp-tablet](https://github.com/googlesamples/android-architecture/tree/dev-todo-mvp-tablet/) | This version adds a master and detail view for tablets. |
| [dev-todo-mvvm-databinding](https://github.com/googlesamples/android-architecture/tree/dev-todo-mvvm-databinding/) | This version uses [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architecture. Note that this version is based on the TODO-MVP-DataBinding sample. |

For information about planned samples, see ["New sample" issues](https://github.com/googlesamples/android-architecture/issues?q=is%3Aissue+is%3Aopen+label%3A%22New+sample%22).

### External samples
[External samples](https://github.com/googlesamples/android-architecture/wiki/External-samples) are variants that may not be in sync with the rest of the branches in this repository.

| Project | Description |
| ------------- | ------------- |
| [todo-mvp-fragmentless](https://github.com/Syhids/android-architecture/tree/todo-mvp-fragmentless) | This version uses [View](https://developer.android.com/reference/android/view/View.html) objects instead of [Fragment](https://developer.android.com/reference/android/app/Fragment.html) objects.|
| [todo-mvp-conductor](https://github.com/grepx/android-architecture/tree/todo-mvp-conductor) | This version uses the [Conductor](https://github.com/bluelinelabs/Conductor) framework to refactor the app to use a single Activity architecture. |

## Why a to-do app?

This app is designed to be simple enough that it can be understood quickly, but complex enough to showcase difficult design decisions and testing scenarios. For more information, see the [app's specification](https://github.com/googlesamples/android-architecture/wiki/To-do-app-specification).

The following screenshot illustrates the UI of the app.

<img src="https://github.com/googlesamples/android-architecture/wiki/images/tasks2.png" alt="A screenshot illustratrating the UI of the app" width="160" style="display: inline; float: right"/>

## Choose a sample for your app

Each sample includes a dedicated `README.md` file where you can find related metrics, as well as subjective assessments and observations by contributors. A number of factors are worth considering when selecting a particular sample for your app, including:

* The size of the app you are developing
* The size and experience of your team
* The amount of maintenance that you are expecting to have to do
* Whether you need a tablet layout 
* Whether you need to support multiple platforms
* Your preference for the compactness of your codebase

For more information on choosing and comparing samples, see the following pages.
* [Samples at a glance](https://github.com/googlesamples/android-architecture/wiki/Samples-at-a-glance)
* [How to compare samples](https://github.com/googlesamples/android-architecture/wiki/How-to-compare-samples)

## Open a sample in Android Studio

To open one of the samples in Android Studio, begin by checking out one of the sample branches, and then open the `todoapp/` directory in Android Studio. The following series of steps illustrate how to open the TODO-MVP sample.

**Note:** The master branch won't compile.

1. Clone the repository:

```
git clone git@github.com:googlesamples/android-architecture.git
```

2. Checkout the TODO-MVP sample:
  
```
git checkout todo-mvp
```

**Note:** To review a different sample, replace `todo-mvp` with the name of project you want to check out.

3. Finally open the `todoapp/` directory in Android Studio.

## Contributors

This project is **built by the community**, and curated by Google as well as other core maintainers.

### External contributors

[David González](http://github.com/malmstein) - Core developer (Content provider sample)

[Karumi](http://github.com/Karumi) - Developer (Clean Architecture sample)

[Natalie Masse](http://github.com/freewheelnat) - Core developer

[Erik Hellman](https://github.com/ErikHellman) - Developer (RxJava sample)

[Saúl Molinero](https://github.com/saulmm) - Developer (Dagger sample)

[Florina Muntenescu](https://github.com/florina-muntenescu) - Developer (RxJava sample)

### Googlers

[Jose Alcérreca](http://github.com/JoseAlcerreca) - Lead/Core developer

[Stephan Linzner](http://github.com/slinzner) - Core developer

[Mustafa Kurtuldu](https://github.com/mustafa-x) - UX/design

[Sharif Salah] (https://github.com/sharifsalah) - Technical Writer

For more information on joining the project, see [how to become a contributor](https://github.com/googlesamples/android-architecture/blob/master/CONTRIBUTING.md) and the [contributor's guide](https://github.com/googlesamples/android-architecture/wiki/Contributions)
