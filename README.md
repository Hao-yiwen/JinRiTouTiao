# 今日头条（JinRiTouTiao）

[![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![minSdk](https://img.shields.io/badge/minSdk-26-brightgreen)](app/build.gradle)
[![Java](https://img.shields.io/badge/Java-8-orange?logo=openjdk&logoColor=white)](app/build.gradle)
[![Gradle](https://img.shields.io/badge/Gradle-8.6-02303A?logo=gradle&logoColor=white)](gradle/wrapper/gradle-wrapper.properties)

仿今日头条的 Android 学习项目：开屏后进入首页、视频、微头条、我的四个底部 Tab，首页和视频按频道拉资讯列表，可以打开图文详情、评论和大图，视频用饺子播放器播放。

代码改自 [chaychan/TouTiao](https://github.com/chaychan/TouTiao)。应用名「今日头条」，`applicationId` 为 `io.github.haoyiwen.jinritoutiao`，`versionName` `1.0.0`（`versionCode` 1）。各 Activity 锁定竖屏。

仓库里没有运行截图，下面的说明都来自当前源码和本环境的构建记录。

## 已实现的功能

- 开屏页停留约 2 秒后进入主界面（`SplashActivity`）。
- 底部四个 Tab：首页、视频、微头条、我的。内容放在 `ViewPager2` 里，禁止手势横滑，通过底部 Tab 切换。
- 首页频道来自 `res/home/values/arrays_news.xml`（推荐、视频、热点、社会等 21 个）。频道名会写入 `SharedPreferences`。频道条右侧的「加频道」图标没有点击逻辑，`OnChannelListener` 的移动/增删方法是空实现。
- 首页顶部「搜你想搜的」是静态文案，没有搜索页。
- 新闻列表按条目形态使用四种布局：纯文字、右侧小图、居中大图、三图。下拉刷新走 `BGARefreshLayout`，请求 `http://is.snssdk.com/` 的 `api/news/feed/v62/`。
- 刷新成功的一页 JSON 会写入 LitePal 表 `NewRecord`（库名 `news`）。再次进入频道时先读本地最后一页。上拉加载更多只读取更早的本地页，不会再向接口要下一页。
- 无网时 OkHttp 使用最多 10MB 的响应缓存；下拉刷新会提示网络不可用。
- 点击图文进入详情：WebView 渲染正文 HTML，展示作者与发布时间，评论列表每页 20 条，可继续加载。底部「写评论…」只有文案，没有提交接口。
- 正文图片通过 JS 桥 `ShowPicRelation` 打开大图浏览（`ViewPager2` + PhotoView），可请求存储权限后用 Glide 把当前图存到外部存储。
- 视频 Tab 使用 `arrays_video.xml` 里的 12 个频道（推荐、音乐、搞笑等），列表样式与新闻列表共用 `NewsListFragment`。有 `parse_video_url` 时直接用 [JiaoZiVideoPlayer](https://github.com/Jzvd/JZVideo) 全屏播放；否则走 `VideoPathDecoder`。
- 视频详情页同样拉正文和评论，并用加速度传感器注册饺子播放器的自动全屏监听。详情页支持 ParallaxBackLayout 侧滑返回；开屏和主界面关闭了侧滑关闭。
- `WebViewActivity` 可以加载 Intent 传入的 URL，并处理页内后退。工程里没有其他页面启动它。

微头条和「我的」只有布局：

- 微头条是标题栏加上「文字 / 图片 / 视频」三块静态入口，`MicroFragment` 只打日志。
- 「我的」展示写死的头像、昵称 `yiwen`、动态 100 / 粉丝 10 / 7 天访客 5，以及收藏、历史、夜间、消息通知等行。`MineFragment` 没有点击或数据请求。

## 架构

界面是 MVP。`BaseActivity`、`BaseFragment` 持有 `BasePresenter`，在销毁时 `detachView()`。列表和详情的接口是 `INewsListView`、`INewsDetailView`，对应 `NewListPresenter`、`NewsDetailPresenter`。View 使用 ViewBinding。

网络是 Retrofit + RxJava 3 + OkHttp。Presenter 在 IO 线程订阅、主线程回调。`TouTiaoApp` 启动时初始化 Logger、LitePal 和侧滑返回。

本地数据有两处：LitePal 保存新闻 JSON 分页；`PreUtils` 用 `SharedPreferences` 保存频道 JSON 和各频道上次刷新时间。

`app/build.gradle` 声明了 `lifecycle-viewmodel`、`lifecycle-livedata`、`fastjson` 和 OkHttp `logging-interceptor`。源码里没有 ViewModel、LiveData、Fastjson 或 `HttpLoggingInterceptor` 的调用，日志拦截器是 `ApiRetrofit` 里手写的。`uikit` 以 `api` 依赖了 ColorTrackTabLayout，频道 Tab 实际使用的是 Material `TabLayout`。

## 技术栈与依赖

| 项 | 版本 / 值 |
| --- | --- |
| Android Gradle Plugin | 8.4.0 |
| Gradle Wrapper | 8.6 |
| compileSdk / targetSdk | 34 |
| minSdk | app 26，uikit 24 |
| Java | 1.8（`sourceCompatibility` / `targetCompatibility`） |
| applicationId | `io.github.haoyiwen.jinritoutiao` |
| versionName / versionCode | 1.0.0 / 1 |
| AndroidX | `useAndroidX=true`，`enableJetifier=true` |

`app` 模块依赖（版本写在 `app/build.gradle` 与 `gradle/libs.versions.toml`）：

| 依赖 | 版本 | 在工程中的用途 |
| --- | --- | --- |
| androidx.appcompat | 1.7.0 | 基础 Activity |
| material | 1.12.0 | TabLayout |
| androidx.activity | 1.9.0 | 版本目录中的 Activity 依赖 |
| constraintlayout | 2.1.4 | 布局 |
| retrofit | 2.11.0 | 接口定义；另有 `adapter-rxjava3`、`converter-gson`、`converter-scalars` 同版本 |
| OkHttp logging-interceptor | 4.12.0 | 已声明，源码未使用该类 |
| RxJava / RxAndroid | 3.1.8 / 3.0.2 | 异步请求 |
| EventBus | 3.3.1 | 注册了刷新和详情关闭事件，对应方法体为空 |
| lifecycle-viewmodel / livedata | 2.7.0 | 已声明，源码未使用 |
| fastjson | 2.0.51 | 已声明，源码未使用 |
| jiaozivideoplayer | 7.7.0 | 视频播放 |
| logger | 2.2.0 | 日志 |
| LitePal | 3.2.3 | 新闻分页缓存 |
| parallaxbacklayout | 1.1.9 | 侧滑返回 |
| MultipleItemRvAdapter | 1.0.4 | 多类型列表（传递依赖 BaseRecyclerViewAdapterHelper） |
| Glide | 4.16.0 | 图片 |
| circleimageview | 3.1.0 | 圆形头像 |
| PhotoView | 2.3.0 | 大图缩放 |
| JUnit | 4.13.2 | 本地示例测试 |
| androidx.test.ext:junit | 1.2.1 | 仪器测试依赖 |
| espresso-core | 3.6.1 | 仪器测试依赖 |

`uikit` 额外依赖：

| 依赖 | 版本 | 用途 |
| --- | --- | --- |
| StateView | 1.5.4 | 加载中、失败重试、空评论 |
| ColorTrackTabLayout | 1.0.1 | 已依赖，界面未使用 |
| Glide | 4.16.0 | 与 app 相同 |

仓库配置在 `settings.gradle`：`google()`、`mavenCentral()`、`jcenter()`、JitPack（写了两次）。`pluginManagement` 同样包含 `google()`、`mavenCentral()`、Gradle Plugin Portal 和 JitPack。

`toutiao-video-downloader` 是独立的 Node 小工具，`package-lock.json` 锁定：

| 依赖 | 版本 |
| --- | --- |
| express | 4.19.2 |
| axios | 1.7.2 |
| cheerio | 1.0.0-rc.12 |

Android 工程没有调用这个服务。

## 目录结构

```text
.
├── app/                          # 应用模块
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── assets/litepal.xml    # LitePal：库 news，映射 NewRecord
│       ├── java/io/github/haoyiwen/jinritoutiao/
│       │   ├── api/              # Retrofit 接口与 OkHttp 客户端
│       │   ├── app/              # Application、接口根地址
│       │   ├── base/             # Activity / Fragment / Presenter 基类
│       │   ├── model/            # entity、response、event
│       │   ├── presenter/        # 列表与详情
│       │   ├── ui/               # activity、fragment、adapter、widget
│       │   └── utils/            # 缓存、图片、视频地址解析
│       └── res/
│           ├── common/           # 开屏、详情、大图、主题、网络配置
│           ├── home/             # 首页、微头条、我的、新闻 item、频道数组
│           ├── video/            # 视频页布局与文案
│           └── mine/             # 「我的」样式
├── uikit/                        # 刷新布局、状态栏、状态页、RecyclerView 封装
├── toutiao-video-downloader/     # 未接入 App 的 Express 视频地址试验
├── gradle/libs.versions.toml
├── build.gradle
├── settings.gradle
└── gradle.properties
```

`app/build.gradle` 的 `sourceSets` 还声明了 `src/main/res/micro`，仓库里没有这个目录，微头条布局在 `res/home`。

接口根地址写在 `ApiConstant`：

- 列表：`http://is.snssdk.com/`
- 视频主机常量：`http://i.snssdk.com`（`URL_VIDEO` 已定义，当前播放路径没有用它）
- 详情页地址在列表点击时拼成 `http://m.toutiao.com/i{itemId}/info/`
- 评论：`article/v2/tab_comments/`

`network_security_config.xml` 允许上述域名以及 `toutiao.iiilab.com`、`www.parsevideo.com` 的明文 HTTP。

## 环境要求

- Android SDK Platform 34、Build-Tools 34.0.0。
- 能跑 Gradle 8.6 / AGP 8.4 的 JDK。`.idea/misc.xml` 记录的是 JDK 17。本环境用 JDK 21 可以编过，javac 会提示 source/target 8 已过时。
- 真机或模拟器需要 Android 8.0（API 26）及以上。
- 运行 `toutiao-video-downloader` 需要 Node.js 与 npm。本环境是 Node v22.23.4、npm 10.9.9。

## 安装与运行

用 Android Studio 打开仓库根目录，或在命令行构建。SDK 路径写在本地的 `local.properties`（已在 `.gitignore` 中，不要提交）：

```properties
sdk.dir=/path/to/Android/sdk
```

按仓库当前的 `settings.gradle` 直接构建，会在解析依赖时失败。2026-11-17 在本环境执行 `./gradlew assembleDebug` 的结果是找不到这四个坐标：

- `org.litepal.guolindev:core:3.2.3`
- `com.github.anzewei:parallaxbacklayout:1.1.9`
- `com.github.nukc.stateview:library:1.5.4`
- `me.weyye.ColorTrackTabLayout:library:1.0.1`

Maven Central 对 LitePal 该坐标返回 404。`jcenter.bintray.com` 会 301 到 Maven Central，所以 `jcenter()` 也拿不到这些包。JitPack 上按 `com.github.anzewei:parallaxbacklayout:1.1.9` 查询同样是 404。这四个包目前能从阿里云 public 仓库下载到。

在 `settings.gradle` 的 `dependencyResolutionManagement.repositories` 里加上：

```gradle
maven { url "https://maven.aliyun.com/repository/public" }
```

本环境没有改仓库文件，而是用一份临时 Gradle init script 加入同一仓库后重新构建。在 JDK 21、Platform 34、Build-Tools 34.0.0 上：

- `./gradlew assembleDebug` 成功，产物 `app/build/outputs/apk/debug/app-debug.apk`（约 9.3MB，debug 签名）。
- `./gradlew test` 成功。测试只有 Android Studio 模板里的 `assertEquals(4, 2 + 2)`，以及未执行的仪器测试模板。

安装到已连接设备：

```bash
./gradlew installDebug
```

本环境没有模拟器，也没有 `adb` 设备，所以上面的安装命令和界面流程没有跑。

视频解析试验（与 App 相互独立）：

```bash
cd toutiao-video-downloader
npm install
node index.js
```

默认监听 `PORT` 或 3000。`GET /get-video-url` 缺少 `url` 时返回 400，正文是 `URL is required`。这一点已在本环境验证。脚本用 axios 拉取页面，再用 cheerio 取第一个 `<video>` 的 `src`，并带了一份写死的西瓜视频 Cookie。没有验证它能解析出真实视频地址。`package.json` 里没有 `start` 脚本。

## 已知问题与待办

- 旧 README 里的两项计划还没有落地：界面仍是 Presenter，新闻缓存仍是 LitePal。工程里没有 Room，也没有把页面改成 ViewModel。`ViewPager2` 已经用在底部 Tab、首页频道、视频频道和大图浏览。
- `VideoPathDecoder` 请求 `https://pv.vlogdownloader.com`，源码注释写明这套解析已经不能用。`ApiService.parseVideo()` 指向 `https://www.parsevideo.com/api.php`，没有被调用。`toutiao-video-downloader` 也没有接到 App。
- 列表上拉只翻本地历史页。频道里还没有缓存时，加载更多会直接结束。
- 正文注入的 JS 用分号拼接图片地址，`ShowPicRelation.getImgArray()` 按逗号拆分，大图页拿到的地址会不对。
- 首页频道编辑、搜索图标、微头条入口、「我的」里的收藏/历史/夜间/设置都没有点击实现。
- `WebViewActivity` 已在 Manifest 注册，没有任何页面跳进去。
- `BasePresenter.onUnsubscribe()` 只在 `CompositeDisposable` 已经 `isDisposed()` 时才 `dispose()`，订阅不会在这里被清掉。
- 新闻详情在 `mItemId.replace(...)` 之前没有判断空字符串。列表数据里如果缺少 `item_id`，点进详情会空指针。2026-11-17 用与客户端相同的 query（当前时间戳、`category` 为空 / `news_hot` / `video`）请求 HTTPS 接口，返回 200 和 JSON，其中一部分条目仍带 `title`、`item_id`、`group_id`，另一部分没有。HTTP 根地址会 301 到 HTTPS。客户端能不能把这条流完整画出来，没有在设备上验证。
- 保存图片走的是运行时 `WRITE_EXTERNAL_STORAGE`。在 targetSdk 34 上，新系统的存储权限行为和这里的实现是否匹配，没有实测。
- 单元测试没有覆盖业务。仓库没有 CI。
- 根目录没有 `LICENSE`。GitHub 仓库也没有设置协议。上游 [chaychan/TouTiao](https://github.com/chaychan/TouTiao) 同样没有协议。`toutiao-video-downloader/package.json` 里的 `ISC` 只属于这个 Node 包。

## 参考

- 上游项目：[chaychan/TouTiao](https://github.com/chaychan/TouTiao)
- 播放器：[Jzvd/JZVideo](https://github.com/Jzvd/JZVideo)
- 列表适配：[chaychan/MultipleItemRvAdapter](https://github.com/chaychan/MultipleItemRvAdapter)

## License

本仓库没有根级 License 文件。使用代码前请自行确认授权。
