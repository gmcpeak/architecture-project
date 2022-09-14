# architecture-project

## Environment
I advise you to use [IntelliJ](https://www.jetbrains.com/idea/)
We are using [https://maven.apache.org/what-is-maven.html](Maven) and openjdk@18. It is a tool to build and manage Java Projects.It also allows us to manage dependencies :)

### Install openjdk@18
[https://jdk.java.net/18/](Link to openjdk@18)

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



Windows, follow this [https://maven.apache.org/install.html](tutorial). 

### How to use maven
[https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html](Read this!)

### Add dependencies
The only one should be Spring(or whatever grady is using :) ) but [https://www.jetbrains.com/help/idea/work-with-maven-dependencies.html#generate_maven_dependency](here) is a tutorial.


### Testing
Follow this [tutorial](https://www.jetbrains.com/help/idea/junit.html). 
There is an example of a Class and its test in this project:)

### PUSHING CODE
run an `mvn clean` before pushing code so we dont have build files, old jars and other stuff in repo :)