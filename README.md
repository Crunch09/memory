# memory
======

Memory project for Android class at THM

## Installation

Memory uses the [ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator) from Jake Wharton.
In order to build memory please [download](https://github.com/JakeWharton/Android-ViewPagerIndicator/tarball/master) this project.

* Now go to __Eclipse__, select __File__->__New__->__Project...__->__Android Project From Existing Code__ and select the
  _library_ folder in your just downloaded ViewPagerIndicator Directory.
* After you inserted the library in your Eclipse workspace, right click on the library-Project select __Android Tools__->__Add Support Library__ and follow the instructions.
* Right click again on the library-Project select __Properties__->__Android__ and makes sure _isLibrary_ is ticked. Now go to
  the __Java Compiler__-Menu and set the __Java Compliance Level__ to __1.6__ because the Project seems not to run with 1.7.
* Finally right click on the memory-Project, select __Properties__->__Android__ and click on the __Add...__-Button on the right side
  and select your library-Project. Now clean and rebuild and you should be good to go.