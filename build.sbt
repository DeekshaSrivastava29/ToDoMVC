
version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.18" % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "4.15.0",
  "io.github.bonigarcia" % "webdrivermanager" % "5.8.0",
  "com.typesafe" % "config" % "1.4.1",
  "org.testng" % "testng" % "7.10.2" % Test,
  "org.scalatestplus" %% "testng-7-5" % "3.2.17.0" % "test"
)
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports", "-f", "junitxml.JUnitXmlReporter")
