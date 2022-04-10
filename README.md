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

### 构建说明
当前仓库不包含 app 模块，需要在根目录下的 local.properties 声明 application 所在的路径

```
appShell=~/Projects/android-application
```
