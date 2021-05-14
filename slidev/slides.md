---
theme: seriph
class: 'text-center'
highlighter: prism
---

# WebView - IPC 构建 APP

---

# 技术栈

- Kotlin
- Vue 3
- Gin
- Room
- Retrofit
- FastJSON
- Docker

<!--
Room
Room 持久性库在 SQLite 的基础上提供了一个抽象层，让用户能够在充分利用 SQLite 的强大功能的同时，获享更强健的数据库访问机制。
该库可帮助使用者在运行应用的设备上创建应用数据的缓存。此缓存充当应用的单一可信来源，使用户能够在应用中查看关键信息的一致副本，无论用户是否具有互联网连接。

Retrofit
Retrofit 和 Java 领域的 ORM 概念类似， ORM 把结构化数据转换为 Java 对象，而 Retrofit 把 REST API 返回的数据转化为 Java 对象方便操作。同时还封装了网络代码的调用。

FastJson
FastJson 是阿里巴巴的开源 JSON 解析库，它可以解析 JSON 格式的字符串，支持将 Java Bean 序列化为 JSON 字符串，也可以从 JSON 字符串反序列化到 JavaBean。
 -->

---

# Introduce

<p class="text-black">
本软件是基于 Webview 桥接 JS 技术实现的 Android 平台的短链接生成软件。客户端平台为 Android，使用 Kotlin 开发,后端使用 Golang 开发，利用 Docker 实现便捷快速的部署服务器。数据库采用关系型数据库 MySQL。利用后端提供的接口服务，用户可以使用本软件将一个较长的网址转换为一个很短的网址。</p>

## WebView

利用 Webview 桥接技术，我们快速开发出了本软件，短链接生成 APP 应当具备以下功能：

1. 利用服务端提供的接口服务，完成长链接到短链接的生成。
1. 利用桥接技术，将 JS 与 Kotlin 联系起来，使得 Web 界面中生成的数据可以缓存在本地中。

---

# Model

持久层使用 Room + SQLite 3

<style>
.vue-monaco, pre {
  
      height: 25rem!important;
      overflow: auto;
}
</style>

<!-- SQLite 的抽象 -->

```kt {monaco}
@Entity(tableName = "urls")
data class URLModel(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("uid")
    var uid: Int,

    @SerializedName("fullUrl")
    @ColumnInfo(name = "full_url")
    val fullUrl: String,

    @ColumnInfo(name = "code")
    @SerializedName("code")
    val code: String,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    val createdAt: Date

) {
    constructor(fullUrl: String, code: String, createdAt: Date) : this(0, fullUrl, code, createdAt)
}
```

---

# Model

```go
type URLInfo struct {
	gorm.Model
	URL     string `json:"url" binding:"url"`
	URLCode string
}
```

---

# Client

## App

应用本体，使用 Kotlin 编写。

<style>
.vue-monaco, pre {
  
      height: 25rem!important;
      overflow: auto;
}
</style>

```sh
.
├── bridge       # 桥接层
├── constants    # 常量
├── model        # 数据相关
├── utils        # 工具类
└── view         # 视图


.
└── repository
    └── url
        ├── URLDatabase.kt
        ├── URLModel.kt
        ├── api
        │   └── URLApi.kt
        ├── dao
        │   └── URLDao.kt
        ├── service
        │   └── URLService.kt
        └── viewmodel
            └── URLViewModel.kt
```

---

# App Bridge

<style>
pre {
      height: 25rem;
      overflow: auto;
}
</style>

```kt {35-}
class WebAppInterfaceBridge(
    private val mContext: Context,
    private val webView: MWebView,

    ) {
    private val logger = Logger.getLogger(WebAppInterfaceBridge::class.java.name)

    @JavascriptInterface
    fun showToast(toast: String): String {

        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()

        return toast
    }

    @JavascriptInterface
    fun openLink(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        (webView.context as MainActivity).startActivity(i)

    }


    @JavascriptInterface
    fun callGeneratorApi(url: String) {

        thread {
            kotlin.run {
                val model = URLService.generatorURL(mContext, url)

                if (model != null) {
                    (webView).emitEventByBus(model, EventType.APPEND)
                }
            }
        }
    }

    @JavascriptInterface
    fun ipcEmitter(eventType: String, payload: String? = "") {
        logger.log(Level.INFO, "EventType: $eventType, payload: $payload")
        val dao = URLDatabase.getDatabase(context = mContext).urlDao
        val data = JSON.parse(payload)
        when (val type = eventType) {
            EventType.WANT_CREATE.name -> {
                val url = (data as JSONObject).getString("data")
                callGeneratorApi(url)
            }

            EventType.REMOVE_ALL.name -> {

            }

            EventType.REMOVE.name -> {

            }

            EventType.REMOVE_MANY.name -> {
                (data as JSONArray).forEach { id ->
                    logger.log(Level.INFO, "$id")
                    dao.deleteById(id as Int)
                }
                this.webView.emitEventByBus(data, EventType.REMOVE_MANY)
            }

            EventType.VISIT.name -> {

            }
            else -> {
                logger.log(Level.INFO, "EventType: $eventType")
            }
        }
    }
}
```

---

# App Bridge

<style>
pre {
      height: 25rem;
      overflow: auto;
}
</style>

```kt
// MWebkitView.kt

fun emitEventByBus(data: Any, eventName: EventType = EventType.DISPATCH) {
    this.post {
        this.evaluateJavascript(
            """
            (() => {
                const bus = window.bus
                if(bus) {
                    bus.emit("${eventName.name}", ${JSON.toJSONString(data)}, true)
                }

            })()

        """.trimIndent()
        ) { e ->
                logger.log(Level.INFO, "[Event: ${eventName.name}]: ${JSON.toJSONString(data)}")
        }
    }

}

private fun registerInterface() {
    this.addJavascriptInterface(WebAppInterfaceBridge(context, this), "Bridge")
}

```

---

# MWebKitView

<style>
.vue-monaco, pre {
  
      height: 25rem!important;
      overflow: auto;
}

</style>

```kt
@SuppressLint("SetJavaScriptEnabled", "ViewConstructor")
class MWebView(context: Context) : WebView(context) {
    private val logger: Logger = Logger.getLogger("logger")


    init {
        this.settings.javaScriptEnabled = true
        if (BuildConfig.DEBUG) {
            this.loadUrl("http://192.168.31.19:3000/")
            setWebContentsDebuggingEnabled(true)
        } else {

            this.loadUrl("file:///android_asset/index.html")
        }

        this.registerInterface()

        this.webChromeClient = object : WebChromeClient() {


            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.apply {
                    Log.d(MWebView::class.java.name, "${message()} -- From line ${lineNumber()} of ${sourceId()}")
                }
                return true
            }
        }
    }


    fun emitEventByBus(data: Any, eventName: EventType = EventType.DISPATCH) {
        this.post {
            this.evaluateJavascript(
                """
            (() => {
                const bus = window.bus
                if(bus) {
                    bus.emit("${eventName.name}", ${JSON.toJSONString(data)}, true)
                }

            })()

        """.trimIndent()
            ) { e ->
                logger.log(Level.INFO, "[Event: ${eventName.name}]: ${JSON.toJSONString(data)}")
            }
        }

    }

    private fun registerInterface() {
        this.addJavascriptInterface(WebAppInterfaceBridge(context, this), "Bridge")
    }
}
```

---

# Entry

<style>
.vue-monaco, pre {
  
      height: 23rem!important;
      overflow: auto;
}

</style>

在 MainActitiy 挂载 MWebView. 网页加载完成，向 renderer 层通信，发送 FETCH_ALL 事件，并把所有 URL items 一同发送

```kt
// MainActivity.kt
class MainActivity : AppCompatActivity() {
    private lateinit var webView: MWebView

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = MWebView(this)
+       webView.webViewClient = object : WebViewClient() {
+           override fun onPageFinished(view: WebView?, url: String?) {
+               super.onPageFinished(view, url)
+               this@MainActivity.init()
+           }
+       }
        setContentView(webView)

    }

+   fun init() {
+       val vm = ViewModelProvider(
+           this,
+           ViewModelProvider.AndroidViewModelFactory.getInstance(application)
+       ).get(URLViewModel::class.java)
+       val urls = vm.getAllUrls()
+
+       urls.observeOnce(Observer { list ->
+           webView.emitEventByBus((list), eventName = EventType.FETCH_ALL)
+       })
+   }

}
```

---

# Renderer (WebView) Emit

<style>
.vue-monaco, pre {
  
      height: 23rem!important;
      overflow: auto;
}

</style>

```js
;(() => {
  class Observable {
    constructor() {
      this.observers = {}
    }

    on(event, handler) {
      const queue = this.observers[event]
      if (!queue) {
        this.observers[event] = [handler]
        return
      }
      const isExist = queue.some((func) => {
        return func === handler
      })
      if (!isExist) {
        this.observers[event].push(handler)
      }
    }

    emit(event, payload) {
      if ('Bridge' in window) {
        console.log(event, payload)
        window.Bridge.ipcEmitter(event, payload)
      }
      const queue = this.observers[event]
      if (!queue) {
        return
      }
      for (const func of queue) {
        func.call(this, payload)
      }
    }

    off(event, handler) {
      const queue = this.observers[event]
      if (!queue) {
        return
      }
      if (handler) {
        const index = queue.findIndex((func) => {
          return func === handler
        })
        if (index !== -1) {
          queue.splice(index, 1)
        }
      } else {
        queue.length = 0
      }
    }
  }

  window.bus = new Observable()
  console.log('event bus mounted')
})()
```

---

# IPC

<style>
.vue-monaco, pre {
  
      height: 23rem!important;
      overflow: auto;
}

</style>

### WebView (JS) <---> Client

```js {25-31}
<template>
  <header>
    <button class="edit-btn btn" @click="handleEdit">
      {{ !isEdit ? '编辑' : '完成' }}
    </button>
    <button class="delete-btn btn" @click="handleDelete" v-if="isEdit">
      删除
    </button>

    <h1>短连接生成器</h1>
    <div class="seq"></div>
  </header>
</template>


<script>
// ...

export default defineComponent({
  name: 'header',
  setup() {
    const envStore = useInjector(EnvStore)
    function handleEdit() {
      envStore.isEditMode.value = !envStore.isEditMode.value
    }
    function handleDelete() {
      const list = envStore.selectedItem.value
      window.bus.emit(EventTypes.REMOVE_MANY, list)
      envStore.isEditMode.value = false
    }
    return {
      handleEdit,
      isEdit: envStore.isEditMode,
      handleDelete,
    }
  },
})

</script>
```

<style>
strong {
  font-size: 0.3em
}
</style>

**所有数据在通讯时均采用 JSON 格式。**

<!-- 而 renderer 层向 app 通信也变得十分简单。只需 emit 事件即可，以 DELETE_MANY 为例。 -->

---

# Diagrams

<style>
p {
  opacity: 1!important
}
</style>

![](https://github.com/Innei/short-link-generator/blob/master/slidev/assets/image.png?raw=true)

---

# Server (Golang)

<style>
.vue-monaco, pre {
  
      height: 25rem!important;
      overflow: auto;
}
</style>

```sh
.
├── config                    # 配置文件
│   ├── config.go
│   └── config.toml
├── controler                 # 接口逻辑实现
│   └── api
│       └── v1
│           └── tinyURL.go
├── data                      # 应用产生数据是存放的位置
│   └── images
│       └── qrcode
├── docker-compose.yml
├── Dockerfile
├── Dockerfile.link           # Dockerfile
├── docs                      # 文档
│   ├── docs.go
│   ├── swagger.json
│   └── swagger.yaml
├── global
│   └── global.go
├── go.mod
├── go.sum
├── initialize
│   ├── email.go
│   ├── gorm.go
│   ├── router.go
│   ├── viper.go
│   └── zap.go
├── log                       # 日志文件
│   └── tinyURL.log
├── main.go                   # 应用入口
├── model
│   ├── dao
│   │   └── tinyURL.go
│   ├── request
│   │   ├── Email.go
│   │   └── URL.go
│   └── URL.go
├── README.md
├── router
│   └── router.go
├── tinyURL
├── utils                     # 工具集
│   ├── Base62.go
│   ├── email.go
│   ├── errCode
│   │   ├── common_code.go
│   │   └── errCode.go
│   ├── qrcode
│   │   └── qrcode.go
│   ├── sha1.go
│   └── validate.go
└── wait-for.sh

```

---

# Server

其中最为核心的短链接生成原理如下：
利用 Mysql 的自增 ID，将此 ID 与原网址进行一一映射，最后对该 ID 进行 Base64 转换，即可得到转换后的短链接码，接口逻辑函数如下:

<style>
.vue-monaco, pre {
  
      height: 23rem!important;
      overflow: auto;
}
</style>

```go

func Long2ShortURL(c *gin.Context) {
	var u request.URLInfo
	err := c.BindJSON(&u)
	ok, msg := utils.Valid(err)
	if !ok {
		errCode.InvalidParams.WithDetails(msg).ResponseJson(c)
		global.LOGGER.Warn("入参错误", zap.Error(err))
		return
	}

	url := model.URLInfo{
		Model:   gorm.Model{},
		URL:     u.URL,
		URLCode: "",
	}
	var urlCode string
	urlCode, err = dao.GenderURLCode(&url)
	if err != nil {
		errCode.GenderURLError.ResponseJson(c)
		global.LOGGER.Error("短链生成失败", zap.Error(err))
		return
	}
	tinyUrl := fmt.Sprintf("http://%s:%d/l/%s", global.APPCONF.Host, global.APPCONF.Port, urlCode)
	errCode.Success.WithData(tinyUrl).ResponseJson(c)
	global.LOGGER.Info("短链生成成功", zap.String("短链", urlCode))
}
```

---
layout: center
---

# End
