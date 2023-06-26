## dev tools
> 当前版本：`TODO` 

### 开始使用
TODO

#### 初始化说明
注册了 content provider 的方式，隐式的完成了初始化，所以属于开箱即用。

不过，你也可以通过 `tools:node="remove"` 方式的方式禁用。

```xml
<provider
    android:authorities="${applicationId}.DevToolsInitProvider"
    android:name=".DevToolsInitProvider"
    tools:node="remove"/>
```

然后手动调用初始化。

```kotlin
DevToolsManager.init(applicationContext)
```

### 集成调试
> 具体可参考 [gradle 文档](https://docs.gradle.org/current/samples/sample_composite_builds_declared_substitutions.html) 

在主工程里 `setting.gradle.kts` 里临时集成，替换具体的版本

```kotlin
// e.g. ~/open-source/foodiestudio/dev-tools
includeBuild("/your_path_contain_this_project") {
    dependencySubstitution {
        substitute(module("com.github.foodiestudio:dev-tools"))
    }
}
```
