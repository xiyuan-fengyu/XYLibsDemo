# XYLibsDemo
自己积累的android开发包，已上传到bintray，用于快速构建新的app


如何使用？<br>
在项目的build.gradle中修改仓库设置<br>
```gradle
allprojects {
    repositories {
        jcenter()

        maven {
            url 'http://dl.bintray.com/xiyuan-fengyu/maven'
        }
    }
}
```
<br>
然后在app的build.gradle中添加依赖
```gradle
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.xiyuan:xylibs:1.0.1'
}
```
