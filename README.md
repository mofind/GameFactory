# 游戏编程中的那些事————SurfaceView的使用
<font color="#aaa">前言：在我还未学习编程之前，曾觉得电脑游戏非常的神奇，后来做了一名移动应用开发者，依然觉得这项技术很是神秘，直到我有幸接触了一些优秀的游戏开发者后，才一一揭开这些神秘面纱，今天就带着大家分享一下游戏编程中的那些事。</font>
<br><br>
<strong>一、SurfaceView的使用</strong>
<br>
<b>Q : 什么是SurfaceView?</b>
<br>
A : 在Android系统中，有一种特殊的视图，称为SurfaceView，它拥有独立的绘图表面，即它不与其宿主窗口共享同一个绘图表面。由于拥有独立的绘图表面，因此SurfaceView的UI就可以在一个独立的线程中进行绘制。又由于不会占用主线程资源，SurfaceView一方面可以实现复杂而高效的UI，另一方面又不会导致用户输入得不到及时响应。
<br>
<b>Q : SurfaceView与View有什么区别和联系?</b>
<br>
A : 普通的Android控件，例如TextView、Button和CheckBox等，它们都是将自己的UI绘制在宿主窗口的绘图表面之上，这意味着它们的UI是在应用程序的主线程中进行绘制的。由于应用程序的主线程除了要绘制UI之外，还需要及时地响应用户输入，否则的话，系统就会认为应用程序没有响应了，因此就会弹出一个ANR对话框出来。对于一些游戏画面，或者摄像头预览、视频播放来说，它们的UI都比较复杂，而且要求能够进行高效的绘制，因此，它们的UI就不适合在应用程序的主线程中进行绘制。这时候就必须要给那些需要复杂而高效UI的视图生成一个独立的绘图表面，以及使用一个独立的线程来绘制这些视图的UI。
<br>
<img src="http://img.my.csdn.net/uploads/201303/11/1363016714_1787.jpg" />
<br>
Activity窗口的顶层视图DecorView及其两个TextView控件的UI都是绘制在SurfaceFlinger服务中的同一个Layer上面的，而SurfaceView的UI是绘制在SurfaceFlinger服务中的另外一个Layer或者LayerBuffer上的。
<br>
注意，用来描述SurfaceView的Layer或者LayerBuffer的Z轴位置是小于用来其宿主Activity窗口的Layer的Z轴位置的，但是前者会在后者的上面挖一个“洞”出来，以便它的UI可以对用户可见。实际上，SurfaceView在其宿主Activity窗口上所挖的“洞”只不过是在其宿主Activity窗口上设置了一块透明区域。
<br>
<b>Q : SurfaceView的其它特性</b>
<br>
SurfaceView采用一种称为“双缓冲”的技术。双缓冲意味着要使用两个缓冲区，其中一个称为Front Buffer，另外一个称为Back Buffer。UI总是先在Back Buffer中绘制，然后再和Front Buffer交换，渲染到显示设备中。
<br>
<br>
<br>
<br>
<br>
<br>
<br><br>
