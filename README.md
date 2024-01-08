# DeepTokensTestBed
An example of how the DeepTokens library can be used

This project builds .j3os during the build process (see build.gradle generateWeatherModel and buildSrc/src/main/java/com/onemillionworlds/DeepTokenifyFile.java) then these are packaged and used in the application at runtime.

Deep tokens can be generated at runtime (it is pretty fast) but generating them ahead of time is the most performant option