# BaranTopalCVSigner
signing a document with a private key in Java

Eclipse project - 1.7 JDK must be in place

Standalone java project with an endpoint that enables signing the pdf. A file, BaranTopal_CV_signed.pdf is generated after the running the application.
The 3rd party library are added manually and referenced relative to the project so it should run as it is. (Sorry, no maven).

You need to run BaranTopalCVPublisher.java first (which is the endpoint publisher), then you need to run BaranTopalCVClient.java and hal.gif
will be applied on the pdf file.

It uses JKS so set a JKS for your need. How to generate JKS keystore is in the file, BaranTopalCVSign.java
