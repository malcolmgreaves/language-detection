# language-detection
Automatically exported from code.google.com/p/language-detection

This is a language detection library implemented in plain Java. (aliases: language identification, language guessing)

Presentation : http://www.slideshare.net/shuyo/language-detection-library-for-java

Abstract
========

Generate language profiles from Wikipedia abstract xml

Detect language of a text using naive Bayesian filter

99% over precision for 53 languages


News
====
03/03/2014
- Distribute a new package with short-text profiles (47 languages). 
- Build latest codes. 
- Remove Apache Nutch's plugin (for API deprecation)


01/12/2012
- Migrate the repository of language-detection from subversion into git for Maven support.

09/13/2011

- Add language profile of Estonian, Lithuanian, Latvian and Slovene.
- Support retrieving a list of loaded language profiles as getLangList() (  issue 20  )
- support generating a language profile from plain text (  issue 23  )
- Fixed a bug of  issue 21 .

02/02/2011
- fixed bugs (no profile directory / long text detectation)

01/24/2011
- 4x faster detection (thanks to elmer.garduno)

12/22/2010
- Support Apache Nutch's plugin

11/18/2010
- Provide a package file.

Requirements
============
- Java 1.6 or later
- JSONIC (bundled) < http://sourceforge.jp/projects/jsonic/devel/ , Apache License 2.0 >
