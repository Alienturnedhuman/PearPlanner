This page documents the licenses of dependencies and library components used in RaiderPlanner to allow the project team to eliminate incompatibilities.  The issue driving this work is [#10](https://github.com/rsanchez-wsu/RaiderPlanner/issues/10).

# Dependencies:  
* commons-validator-1.6.jar  
* guava-21.0.jar  
* hamcrest-all-1.3.jar  
* hamcrest-core-1.3.jar  
* hamcrest-generator-1.3.jar  
* hamcrest-integration-1.3.jar  
* hamcrest-junit-2.0.0.0.jar  
* hamcrest-library-1.3.jar  
* jfoenix.jar  
* jfxtras-agenda-8.0-r5.jar  
* jfxtras-common-8.0-r5.jar  
* jfxtras-controls-8.0-r5.jar  
* junit-4.12.jar  
* testfx-core-4.0.0-20150226.214553-8-sources.jar  
* testfx-core-4.0.6-alpha.jar
* testfx-junit-4.0.6-alpha.jar
* testfx-junit5-4.0.6-alpha.jar
* testfx-legacy-4.0.6-alpha.jar


# Licenses:

## Apache 2.0
No license conflict
"This is a free software license, compatible with version 3 of the GNU GPL."

### Dependencies under this license
guava-21.0.jar  [Source](https://mvnrepository.com/artifact/com.google.guava/guava/21.0)  
jfoenix.jar  [Source](http://www.jfoenix.com/)  
commons-validator-1.6.jar  [Source](https://mvnrepository.com/artifact/commons-validator/commons-validator/1.6)  

## BSD 2-clause
No license conflict
"The newer versions of the original BSD licenses, i.e., the 3-clause and the 2-clause variants are compatible with GPL."

### Dependencies under this license  
hamcrest-core-1.3.jar  [Source](https://mvnrepository.com/artifact/org.hamcrest/hamcrest-core/1.3)  
hamcrest-all-1.3.jar  [Source](https://mvnrepository.com/artifact/org.hamcrest/hamcrest-all/1.3)  
hamcrest-generator-1.3.jar  [Source](https://mvnrepository.com/artifact/org.hamcrest/hamcrest-generator/1.3)  
hamcrest-integration-1.3.jar  [Source](https://mvnrepository.com/artifact/org.hamcrest/hamcrest-integration/1.3)  
hamcrest-library-1.3.jar  [Source](https://mvnrepository.com/artifact/org.hamcrest/hamcrest-library/1.3)  
jfxtras-agenda-8.0-r5.jar  [Source](https://mvnrepository.com/artifact/org.jfxtras/jfxtras-agenda/8.0-r5)  
jfxtras-controls-8.0-r5.jar  [Source](https://mvnrepository.com/artifact/org.jfxtras/jfxtras-controls/8.0-r5)  
jfxtras-common-8.0-r5.jar  [Source](https://mvnrepository.com/artifact/org.jfxtras/jfxtras-common/8.0-r5)  
jfxtras-labs-samples-8.0-r6-20160701.060717-1.jar  [Source](https://github.com/JFXtras/jfxtras-labs-samples)  

## European Union Public License version 1.1 (EUPL 1.1)
Licensing conflict.  
"This is a free software license. By itself, it has a copyleft comparable to the GPL's, and incompatible with it."
EUPL allows relicensing to GPL version 3, because there is a way to relicense to the CeCILL v2, and the CeCILL v2 gives a way to relicense to any version of the GNU GPL.

To do this two-step relicensing, you need to first write a piece of code which you can license under the CeCILL v2, or find a suitable module already available that way, and add it to the program. Adding that code to the EUPL-covered program provides grounds to relicense it to the CeCILL v2. Then you need to write a piece of code which you can license under the GPLv3+, or find a suitable module already available that way, and add it to the program. Adding that code to the CeCILL-covered program provides grounds to relicense it to GPLv3+.

### Dependencies under this license
testfx-core-4.0.0-20150226.214553-8-sources.jar  [Source](https://mvnrepository.com/artifact/org.testfx/testfx-core/4.0.0-alpha)  
testfx-core-4.0.6-alpha.jar  [Source](https://mvnrepository.com/artifact/org.testfx/testfx-core/4.0.6-alpha)  
testfx-junit5-4.0.6-alpha.jar  [Source](https://mvnrepository.com/artifact/org.testfx/testfx-junit5/4.0.6-alpha)  
testfx-junit-4.0.6-alpha.jar [Source](https://mvnrepository.com/artifact/org.testfx/testfx-junit/4.0.6-alpha)  
testfx-legacy-4.0.6-alpha.jar  [Source](https://mvnrepository.com/artifact/org.testfx/testfx-legacy/4.0.6-alpha)  

## Eclipse Public License version 1.0 (EPL 1.0)
Licensing conflict  
"Unless you are the owner of the software or have received permission from the owner, you are not authorized to apply the terms of another license to the Program by including it in a program licensed under another Open Source license." [Source](https://eclipse.org/legal/eplfaq.php#USEINANOTHER)  

### Dependencies under this license
junit-4.12.jar  [Source](https://mvnrepository.com/artifact/junit/junit/4.12)  
hamcrest-junit-2.0.0.0.0.jar  [Source](https://mvnrepository.com/artifact/org.hamcrest/hamcrest-junit/2.0.0.0)  


