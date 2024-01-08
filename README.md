[[[BLUID]]]

"mvn compile" -----for compiler


[[[RUN]]]
" mvn exec:java -Dexec.mainClass="Main" -----for run


As long as you make sure to keep the `metadata.yml` file at the root of this repository, you are free to do anything. Our suggestion would be to have it organized into something like the following:

    .gitignore
    metadata.yml
    pom.xml
    README.md
    src/
      main/
      test/
    doc/
      uml/
      report.pdf

In particular, you will soon have to replace this README wit~~h something that describes your own project.