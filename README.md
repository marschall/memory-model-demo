
Java implementation of Nick Wilcox's [Examining ARM vs X86 Memory Models with Rust](https://www.nickwilcox.com/blog/arm_vs_x86_memory_model/)

Native Image
------------

Be sure to run

```
gu install native-image
```

Epsilon GC
----------

```
java -Xmx64m -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -jar target/memory-model-demo-0.1.0-SNAPSHOT.jar
```

JIT Watch
---------

```
java -Xmx64m -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -jar target/memory-model-demo-0.1.0-SNAPSHOT.jar
```
