# Csv Plugin for Graylog

[![Build Status](https://travis-ci.org/alcampos/graylog-plugin-function-csv.svg?branch=master)](https://travis-ci.org/alcampos/graylog-plugin-function-csv)

Graylog plugin to parse CSV messages in the pipeline feature

**Required Graylog version:** 2.0 and later

Installation
------------

[Download the plugin](https://travis-ci.org/alcampos/graylog-plugin-function-csv/releases)
and place the `.jar` file in your Graylog plugin directory. The plugin directory
is the `plugins/` folder relative from your `graylog-server` directory by default
and can be configured in your `graylog.conf` file.

Restart `graylog-server` and you are done.

Usage
-----

This is the prototype of the function:


Getting started
---------------

This project is using Maven 3 and requires Java 7 or higher.

* Clone this repository.
* Run `mvn package` to build a JAR file.
* Optional: Run `mvn jdeb:jdeb` and `mvn rpm:rpm` to create a DEB and RPM package respectively.
* Copy generated JAR file in target directory to your Graylog plugin directory.
* Restart the Graylog.

Plugin Release
--------------

We are using the maven release plugin:

```
$ mvn release:prepare
[...]
$ mvn release:perform
```

This sets the version numbers, creates a tag and pushes to GitHub. Travis CI will build the release artifacts and upload to GitHub automatically.
