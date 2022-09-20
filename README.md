# architecture-project

## Environment
I advise you to use [IntelliJ](https://www.jetbrains.com/idea/)
We are using [Maven](https://maven.apache.org/what-is-maven.html). It is a tool to build and manage Java Projects.It also allows us to manage dependencies :)

We are also using [openjdk@18](https://jdk.java.net/18/).

### Install openjdk@18
[openjdk@18](https://jdk.java.net/18/)

If you are on Mac, use homebrew
`
brew install openjdk@18
`

Ubuntu:
1) Download from the link above
2) Do this:
`
tar xvf openjdk-18*_bin.tar.gz
`
3) Add to path 

Windows:
1) See ubuntu instructions.

### Install Maven
If you are on Mac, use homebrew!

`
brew install maven
`

Ubuntu, use apt.

`sudo apt install maven`



Windows, follow this [tutorial](https://maven.apache.org/install.html). 

### How to use maven
[Read this!](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

### Add dependencies
The only one should be Spring(or whatever grady is using :) ) but [here](https://www.jetbrains.com/help/idea/work-with-maven-dependencies.html#generate_maven_dependency) is a tutorial.


### Testing
Follow this [tutorial](https://www.jetbrains.com/help/idea/junit.html). 
There is an example of a Class and its test in this project:)

### PUSHING CODE
run an `mvn clean` before pushing code so we dont have build files, old jars and other stuff in repo :)
