# Psy4J 1.3.0

> A cognitive science package based on object-oriented programming ideas. The program is driven by the JavaFx framework and the JVM platform.

Psy4J means **Psychology ToolKit For Java Virtual Machine Platform**，The program is written in Java and Scala and built on the JavaFx GUI platform. 

In short, Psy4J tries to make your psychology experiment programming as easy as "making PPT". You only need to use JavaFX components to decorate each page to present the stimulate, and use the tools we provide to simplify the keyboard and mouse events. Processing, providing experimental feedback, etc., Psy4J will automatically arrange the pages in order and trigger them one by one at a time, and perform the data persistence process after the experiment is over.

![](http://static2.mazhangjing.com/badge/openjdk.png)
![](http://static2.mazhangjing.com/badge/javafx.png)
![](http://static2.mazhangjing.com/badge/scala.png)

## Usage

Simply add the following dependencies to your Maven/Gradle/sbt project to use Psy4J (last version)

```xml
<dependency>
    <groupId>com.mazhangjing</groupId>
    <artifactId>psy4j</artifactId>
    <version>1.3.0</version>
</dependency>
```

> Note: An "actual" psychology experiment is provided under the test folder for reference. Using Psy4J, this experiment took 3 hours to develop.

> Note: Psy4J relies on some commonly used libraries, such as LogBack, commonsIO, TypeSafe Config, Play JSON and ScalaFx. You can use them directly in the project without adding additional dependencies.

## Getting Started

The following is a simple "HELLO, WORLD" program. You can embed the Psy4J code into any JavaFx program paragraph. For example, a button click event is triggered and the Psy4J experiment window is opened-in order to initialize an experiment, you need to define:

- One or more experimental stimuli of Screen class inherited from `Screen/ScreenAdaptor`, you only need to define `layout` variable (JavaFx interface) and `duration` variable (presentation time) in `initScreen` method, and define the processing logic of buttons or any event in `eventHandler` method.
- One or more experimental sequences that inherit the `Trial` class and contain multiple `Screen` class instance collections, only need to rewrite `initTrial` method and add `Screen` class instances to the `screens` variable collection.
- One or more complete experiments that inherit the `Experiment` class and contain multiple sets of `Trial` class instances. You only need to override `initExperiment` method to set them. In addition, the `Experiment` class allows you to save data before closing the experiment, such as serializing to JSON/ In the CSV file, you only need to rewrite the `saveData` method.

Note: The following program uses Scala’s JavaFx DSL: [scalafx](http://www.scalafx.org/)

```scala
import com.mazhangjing.lab.{Experiment, BasicScreen, ScreenAdaptor, Trial}
import com.mazhangjing.utils.ExpStarter
import javafx.event.Event
import javafx.scene.input.KeyCode
import javafx.scene.{Scene => JScene}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.StringProperty
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.StackPane
import scalafx.scene.text.Font

class DemoExperiment extends Experiment {
  override def initExperiment(): Unit = {
    trials.add(new Trial {
      override def initTrial(): Trial = {
        screens.add(new ScreenAdaptor {
          private val info = StringProperty("HELLO WORLD")

          override def callWhenShowScreen(): Unit = println("Before Show")

          override def callWhenLeavingScreen(): Unit = println("After Show")

          override def initScreen(): BasicScreen = {
            layout = new StackPane {
              children = Seq(new Label {
                text <==> info
                font = Font(30)
              })
            }
            duration = 50000
            this
          }

          override def eventHandler(event: Event, experiment: Experiment, scene: JScene): Unit = {
            ifKeyIn(event) { c => {
              info.set(s"You Pressed ${c.getName}")
              if (c == KeyCode.F) goNextScreenSafe
            }
            }
          }
        }.initScreen())
        this
      }
    }.initTrial())
  }

  override def saveData(): Unit = {
    println("Saving Data Here...")
  }
}

object HelloWorld extends JFXApp {
  stage = new PrimaryStage {
    s =>
    scene = new Scene(400, 300) {
      root = new StackPane {
        children = Seq(new Button("Run") {
          onAction = _ => {
            new ExpStarter {}.runExperiment("DemoExperiment", fullScreen = false, s)
          }
        })
      }
    }
  }
}
```

## Structure

![](https://static2.mazhangjing.com/20190218/4f4630d_psy4j.png)

> Pure Java Implement Psy4J (before version 1.2) can be fond in [Here](https://github.com/corkine/psy4jOld)

## Real World Experimental Example

Experimental example developed using Psy4J: 

- [lhlExp](https://gitee.com/corkine/lhlExp) :: Basic psychology, experiments on memory and cognitive ability 
- [xkExp](https://gitee.com/corkine/xk-experiment) :: Developmental and Educational Psychology, using Psy4J technology for electronic questionnaire tests and online "quiz"
- [fxwExp](https://gitee.com/corkine/fxw-experiment) :: Personality psychology, using Psy4J for online "quiz" and real-time collection of "video clips" of participants solving problems. 
- [rhythmExp](https://gitee.com/corkine/rhythmExp) :: Congnitive psychology, Perception experiment of "rhythm and synchronization" based on the early version of Psy4J 
- [wshExp & zswExp](https://gitee.com/corkine/psy4j-early) :: The most original version of Psy4J was developed based on the need to write these two experiments. These two experiments in the field of cognitive psychology used sound detection technology and mouse tracking technology with Psy4J for experiments. 
- [timeSegExp2019](https://gitee.com/corkine/timeSegExp) :: Cognitive psychology, experiments in the field of time perception, combining MATLAB and Psy4J 
- [timeSegExp2020](https://gitee.com/corkine/masterThesisExp) :: Congnitive psychology, A variety of experimental techniques (language, framework, etc.) are combined to demonstrate Psy4J's tailoring capabilities. 

## Why Psy4J?

**Psy4J is feature-rich**: In addition to the core functions, Psy4J provides functions such as "view angle and pixel conversion", "automatic sound collection", "simple pure tone production" and simple statistical analysis functions. Nevertheless, we still recommend using Python or MATLAB for more detailed and professional data analysis. For example, in my experience, I often use Psy4J as a tool for experiment arrangement, stimulus generation, and user data collection, and save the collected data as JSON files, and perform data analysis and statistical analysis in Python and MATLAB.

**Psy4J is easy to tailor**: you can integrate Psy4J into any code in Java or Scala or other JVM languages ​​(even languages ​​supported by GraalVM, perhaps), and trigger execution in various ways, all based on JVM Advantage. In my experience, at some point, I used MATLAB's built-in JVM to call my own Psy4J code to generate the required pictures and return MATLAB variable data and present the stimulus in MATLAB's Psychtoolbox package. At other times, I created a simple sbt task to automatically trigger the MATLAB instance to start data analysis after completing the experiment in Psy4J. In some extreme cases, I even integrated Java + Scala + Python + Clojure + MATLAB in an experiment. For the "visual" cognitive research I am engaged in, in order to perform high-performance experiments and share code with colleagues, I generally use Scala and Psy4J to write pre-experiments. In the formal experiment, I use Psy4J to generate the required pictures and use Scala to compile the program, and then import it into MATLAB and use Psychtoolbox to display pictures and collect data. Based on a set of simple "JSON" experiment files, colleagues can read and review MATLAB code. In addition, I can also efficiently use the richness of JavaFx Interface display capabilities and Scala's type safety capabilities to ensure that the experiment is error-free and easy to organize, maintain, and iteratively update (for dynamic languages, it is annoying to constantly modify parameters and experimental schemes, so we need static Language for organizational reconstruction and code reuse).


## Write to psychologists
### Techniques necessary to run the code
- Knowledge about JDK installation and operation [Official website](https://www.oracle.com/java/technologies/javase-downloads.html)
- sbt (Simple Build Tools): Scala/Java project construction knowledge [Official document](https://www.scala-sbt.org/1.x/docs/)

### Understand the techniques required to debug a program
- Scala language (stimulus generation) [Official website](https://www.scala-lang.org/)
- Java language (basic framework) [Official website](https://www.oracle.com/java/technologies/javase-downloads.html)
- sbt build tool (engineering build) [official website](https://www.scala-sbt.org)
- Maven warehouse concept (engineering construction) [Official website](http://maven.apache.org/)
- Play JSON package (JVM, JSON data format conversion) [Project site](https://github.com/playframework/play-json)
- Logback package (JVM, logging) [project site](http://logback.qos.ch/)
- ScalaFX package (JVM, GUI rendering package) [project site](https://github.com/scalafx/scalafx)
- JavaFx package (JVM, GUI rendering basis) [Project site](https://docs.oracle.com/javase/8/javafx/api/)
- ScalaTest package (JVM, test) [project site](https://www.scalatest.org/)
- JsonLab package (MATLAB, JSON data format conversion) [project site](https://github.com/fangq/jsonlab)

## Precautions

The latest version of Psy4J has been modified to better integrate Scala Trait—Mixin style development, and work with ScalaFx. Various classes have undergone certain revisions and can now be used with Scala Trait (Java protected and Scala inherited semantics are different, so the keywords of common APIs have been adjusted and set to be public, such as Trial screens, logger, etc.). In addition, Scala's ScreenAdaptor has been expanded, and commonly used implicit injections and methods have been added, and LabUtils have been mixed in to provide a simpler API experience (JavaFx event judgment, timer tasks, etc.).

### Psy4J's own suggestions:

- Psy4J has a built-in logger, using Logback/Slf4J log library, which can be used directly.

- The Psy4J timer is single-threaded, so do not enable multiple timers at the same time. It is best to nest the call and create a new timer in the outer callback function.

### Psy4J and Scala integration suggestions

- The implementation class of an experiment cannot be a trait, because Java treats traits as an Interface, and an experiment is called through reflection, and naturally cannot be initialized. The correct way is to set it to class-which is also semantically correct.

- It is recommended to use traits for Screen implementation classes. Configuration items and Screen dependencies can be provided by mixing trait mixin into Config. This way of writing is very efficient and does not require frequent changes to the constructor to pass in parameters.

- For the Config configuration trait, you need to be aware that when you use it in a trial to mix it with Screen to construct a Screen instance, inheriting and overloading its methods in the trial will not affect the value of Config in the Screen. The reason is simple, because trait mixins are very different from Object, and the traits being mixin contain copies of them, so the traits of the same trait in multiple mixins do not share the variables of this trait. This avoids the state problems caused by variable variables in large programs, but sometimes, involuntarily use it incorrectly: for example, mixing Config in Trial to modify a value, hoping that this value will take effect in its Screen with Config-this is impossible. Although it does cause inconvenience to a certain extent. The solution is simple. Screen passes in the value of the mixin through the Config field of the Trial mixin, or creates a Config subclass of itself, rewrites a certain field, and then gives the mixin to its own Screen, although it is more Troublesome, but still more convenient than the constructor to pass parameters. 
