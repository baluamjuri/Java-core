<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="HandheldFriendly" content="True">
    <meta name="MobileOptimized" content="480">
    <meta name="viewport" content="width=device-width, height=device-height initial-scale=1, user-scalable=1">

    <title>Java BlockingQueue</title>

    <meta name="description" content="The Java BlockingQueue interface represents a queue that is thread safe, meaning multiple threads can insert and remove elements concurrently. This Java BlockingQueue tutorial explains how a BlockingQueue works, and introduces the several different BlockingQueue implementations that are built into Java.">
    <meta name="keywords"    content="java util concurrent blockingqueue blocking queue">
    <meta name="author" content="Jakob Jenkov">

    <meta name="twitter:card" content="summary_large_image" >
    <meta name="twitter:site" content="@jjenkov">
    <meta name="twitter:title" content="Java BlockingQueue">
    <meta name="twitter:description" content="The Java BlockingQueue interface represents a queue that is thread safe, meaning multiple threads can insert and remove elements concurrently. This Java BlockingQueue tutorial explains how a BlockingQueue works, and introduces the several different BlockingQueue implementations that are built into Java.">
    <meta name="twitter:creator" content="@jjenkov">
    <meta name="twitter:domain" content="jenkov.com">

    <meta name="twitter:image:src" content="http://tutorials.jenkov.com/images/java-concurrency-utils/java.util.concurrent-teaser-500-300.png">

    <meta name="og:type"  content="article"/>
    <meta name="og:title" content="Java BlockingQueue"/>
    <meta property="og:url"   content="http://tutorials.jenkov.com/tutorials/java-util-concurrent/blockingqueue.html"/>
    <meta property="og:description" content="The Java BlockingQueue interface represents a queue that is thread safe, meaning multiple threads can insert and remove elements concurrently. This Java BlockingQueue tutorial explains how a BlockingQueue works, and introduces the several different BlockingQueue implementations that are built into Java."/>

    <meta property="og:image" content="http://tutorials.jenkov.com/images/java-concurrency-utils/java.util.concurrent-teaser-500-300.png"/>

    <meta name="google-site-verification" content="i_TwzdElg-by5uXLvyAjuIaCjxo0yjtW8LdRPUDEEcw" />

    <link rel="icon" href="/favicon.ico"  type="image/x-icon">
</head>

<body>
<!-- Layout specific styles -->
<style>
#body{ margin: 0px; font-family: Arial; background-color: #222222; color: #ffffff;}
body{ margin: 0px; font-family: Arial; background-color: #f0f0f0; color: #ffffff;}
#header{ height: 66px; background-color: #000000; position:fixed; top: 0px; left: 0px; width: 100%; box-shadow: 0px 8px 6px -6px #999999;}

#menuButton { width: 20px; height: 18px; position: absolute; top: 24px; left: 24px; }
#menuButton div { position:relative; height: 2px; width: 20px; margin-bottom: 4px; background-color: #ffffff; }
#menuButton:hover{ cursor: pointer; }
#topBarLogo { position:relative; left: 0px; top: 5px; width: 370px; }
#logoLink { position:relative; top: -8px; left: 236px; height: 40px; width: 300px; }
#logoLink:hover{ cursor: pointer; }

#menuItems { width: 100%; background-color: rgba(0,0,0,0.8); display:none; position: fixed; top: 66px; left: 0px; width: 100%;}
.menuItem { color: #ffffff; display: inline-block; padding: 24px 24px; }
#menuItems a { color:#ffffff; text-decoration: none; }

#footer {
    padding: 24px;
    margin: 16px 0px 0px 0px;
    background-color: #000000;
}
</style>
<div id="header">

    <div id="topBarLogo" >
        <svg x="0px" y="0px" width="380px" height="48px" viewBox="0 0 380 100" style="enable-background:new 0 0 380 100;" xml:space="preserve">
        <path d="M3,32 l10,0  l0,26  c-7,21 -30,21 -53,10  l4,-9  c18,11 33,10 39,-3  z " style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M19,32 l50,0  l0,9  l-50,0 z" style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M19,49 l50,0  l0,9  l-50,0 z" style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M19,65 l50,0  l0,9  l-50,0 z" style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M76,32 l13,0  l40,31  l0,-31  l10,0  l0,42  l-10,0 l-43,-32 l0,32 l-10,0 z " style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M129,32  l10,0  l0,15 l5,0  l32.5,-14.5  l19,0  l-41,19  l40,22  l-19,0  l-33,-18  l-3.5,0  l0,19  l-10,0 z " style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M188,49  c10,-24 60,-24 70,0  l-11,0  c-10,-12 -38,-12 -48,0  z " style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M188,57  c10,24 60,24 70,0  l-11,0  c-10,12 -38,12 -48,0  z " style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M254,32  l13,0  l25,34 l25,-34  l13,0  l-32,42 l-12.5,0 z" style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M319,65  l12,0 l0,8 l-12,0 z" style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M340,53  c3,-27 54,-27 67,-10  l-15,0  c-10,-5 -40,-5 -41.5,10 l-10,0  " style="stroke: none;  stroke-width: 1; fill: #ffffff;"></path>
            <path d="M340,53  c3,27 54,27 67,10  l-15,0  c-10,5 -40,5 -41.5,-10 l-10,0  " style="stroke: none;  stroke-width: 1; fill: #ffffff;"></path>
            <path d="M410,49  c10,-24 60,-24 70,0  l-11,0  c-10,-12 -38,-12 -48,0  z " style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M410,57  c10,24 60,24 70,0  l-11,0  c-10,12 -38,12 -48,0  z " style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
            <path d="M484,34  l10,0  l0,6 c13,-10 26,-10 37,0  c12,-10 32,-10 39,3  l0,30  l-10,0  l0,-27  c-3,-9 -24,-9 -28,3 l0,24  -10,0  l0,-27  c-3,-9 -24,-9 -28,3 l0,24  -10,0 z" style="stroke: none; stroke-width: 1; fill: #ffffff;"></path>
        </svg>
    </div>

    <div id="logoLink">Tech &amp; Media Labs</div>


    <div id="menuButton">
        <svg x="0px" y="0px" width="24px" height="24px" viewBox="0 0 24 24"style="enable-background:new 0 0 24 24;" xml:space="preserve">
            <rect x="0" y="0" width="24" height="24" style="stroke:none; fill: #000000;"></rect>
            <path d="M0,2 l20,0" style="stroke: #ffffff; stroke-width: 2; fill: none;"></path>
            <path d="M0,8 l20,0" style="stroke: #ffffff; stroke-width: 2; fill: none;"></path>
            <path d="M0,14 l20,0" style="stroke: #ffffff; stroke-width: 2; fill: none;"></path>
        </svg>
    </div>

</div>

<div id="menuItems">
    <div class="menuItem"><a href="http://tutorials.jenkov.com">Tutorials</a></div>
    <!--
    <div class="menuItem">Tools</div>
    <div class="menuItem">Themes</div>
    <div class="menuItem">News</div>
    -->
    <div class="menuItem"><a href="/rss.xml">RSS</a></div>
    <div class="menuItem"><a href="/">Home</a></div>
    <!--
    <div class="menuItem">About</div>
    -->
</div>

<div style="height: 82px;"></div>

<!-- Navigation Specific Styles -->
<style>
#navWrapper{
  #visibility: hidden;
  display: inline-block;
  height: calc(100% - 164px);
  background-color: #f0f0f0;
  width: 412px;
  color: #333333;
}
#navigation{
  display: inline-block;
}
#navigation a{
  color: #000000;

}
#navigation>div{
  display: inline-block;
  border-right: 1px solid #d0d0d0;
  vertical-align: top;
  cursor: pointer;
}
#navigation>div>div{
  #padding: 6px 12px;
  #border-bottom: 1px solid #000000;
  border-top: 1px solid #d0d0d0;
  #background-color: #d8d8d8;
}
#navThemes{
  #width: 150px;
  box-sizing: border-box;
  #visibility: hidden;
  display: none;
}
#navThemes>div{
  padding: 6px 12px 6px 24px;
  border-bottom: 1px solid #d0d0d0;
  display: none;
}
#navTopics{
  width: 160px;
  box-sizing: border-box;
}
#navArticles{
  width: 250px;
  box-sizing: border-box;
}
#navTopics>div, #navArticles>div{
  padding: 6px 12px;
  border-bottom: 1px solid #d0d0d0;
}
.selected{
  font-weight: bold;
  background-color: #ffffff;
}
.notSelected{
  #background-color: #ffffff;
}
#navPath{
  padding: 8px 8px 8px 22px;
  color: #666666;
  font-size: 0.9em;
  background-color: #ffffff;
  border: 1px solid #d0d0d0;
  box-shadow: 0px 4px 3px -3px #999999;
  margin-bottom: 8px;
}
#navPath>a {
  cursor: pointer;
  font-weight: bold;
}
</style>


<!-- article specific styles -->
<style>
#mainAreas { width: 100%; }
.mainArea { display: inline-block; width: 25%; background-color: #2222222; padding: 24px; box-sizing: border-box;
            border-top: 1px solid #333333; border-left: 1px solid #333333; border-bottom: 1px solid #000000; border-right: 1px solid #000000;
            text-align: center; color: #ffffff; font-size: 1.2em;
}
</style>
<style>
#main {
  display: inline-block;
  text-align: left;
  vertical-align: top;
  padding: 20px 30px;
  margin: 0px 0px 0px 2px;
  box-sizing: border-box;
  background-color: #ffffff;
  color: #000000;
  border: 1px solid #d0d0d0;
  box-shadow: 0px 8px 6px -6px #999999;
}

@media only screen and (min-width: 1px) and (max-width: 1000px) {
    #navWrapper { display: none; }
    #main{ width: calc(100% - 8px); max-width: 1000px; box-sizing: border-box; }
}
@media only screen and (min-width: 1000px) {
    #main{ width: calc(100% - 430px); max-width: 1000px;box-sizing: border-box; }
}


#trailToc{
  visibility: hidden;
}
#pageToc{
    border: 1px solid #eeeeee;
}
#pageToc a{
    text-decoration: none;
}
#lastUpdate{
    border: 1px solid #eeeeee;
    border-top: none;
    color: #666666;
    font-size: 0.8em;
}
a{text-decoration: none; }
#pageToc a{color: #000066; }
#mainBody a{font-weight: bold; color: #000066; }
#mainBody img{ max-width: 100%; }
#next a{font-weight: bold; color: #000066; }
#next{
    border-top: 1px solid #eeeeee;
    border-bottom: 1px solid #eeeeee;
    padding: 8px 0px;
    font-size: 1.2em;
}
.codebox, .codeBox {
    border: 1px solid #cccccc;
    padding: 8px;
    font-family: monospace monospace;
    background-color: #f0fff0;
}
</style>
<style>
#rightWrapper{
    display: inline-block;
    color: #000000;
    vertical-align: top;
    background-color: #ffffff;
    border: 1px solid #d0d0d0;
    box-shadow: 0px 8px 6px -6px #999999;

    margin: 0px 0px 0px 8px;
    padding: 20px;
  }
@media only screen and (min-width: 1px) and (max-width: 1650px) {
    #rightWrapper{ display: none; }
}
@media only screen and (min-width: 1650px) {
    #rightWrapper{ display: inline-block; }
}


</style>

<div id="mainWrapper">
    <div id="navWrapper">
        <div id="navPath"></div>
        <div id="navigation">
            <div id="navThemes"></div><div id="navTopics"></div><div id="navArticles"></div>
        </div>
        <div id="trailToc" >
            <div id='trailTitle'>java.util.concurrency - Java Concurrency Utilities</div><ol><li><a href="/tutorials/java-util-concurrent/index.html">Java Concurrency Utilities - java.util.concurrent</a></li><li><a href="/tutorials/java-util-concurrent/blockingqueue.html">Java BlockingQueue</a></li><li><a href="/tutorials/java-util-concurrent/arrayblockingqueue.html">ArrayBlockingQueue</a></li><li><a href="/tutorials/java-util-concurrent/delayqueue.html">DelayQueue</a></li><li><a href="/tutorials/java-util-concurrent/linkedblockingqueue.html">LinkedBlockingQueue</a></li><li><a href="/tutorials/java-util-concurrent/priorityblockingqueue.html">PriorityBlockingQueue</a></li><li><a href="/tutorials/java-util-concurrent/synchronousqueue.html">SynchronousQueue</a></li><li><a href="/tutorials/java-util-concurrent/blockingdeque.html">BlockingDeque</a></li><li><a href="/tutorials/java-util-concurrent/linkedblockingdeque.html">LinkedBlockingDeque</a></li><li><a href="/tutorials/java-util-concurrent/concurrentmap.html">ConcurrentMap</a></li><li><a href="/tutorials/java-util-concurrent/concurrentnavigablemap.html">ConcurrentNavigableMap</a></li><li><a href="/tutorials/java-util-concurrent/countdownlatch.html">CountDownLatch</a></li><li><a href="/tutorials/java-util-concurrent/cyclicbarrier.html">CyclicBarrier</a></li><li><a href="/tutorials/java-util-concurrent/exchanger.html">Exchanger</a></li><li><a href="/tutorials/java-util-concurrent/semaphore.html">Semaphore</a></li><li><a href="/tutorials/java-util-concurrent/executorservice.html">Java ExecutorService</a></li><li><a href="/tutorials/java-util-concurrent/java-callable.html">Java Callable</a></li><li><a href="/tutorials/java-util-concurrent/java-future.html">Java Future</a></li><li><a href="/tutorials/java-util-concurrent/threadpoolexecutor.html">ThreadPoolExecutor</a></li><li><a href="/tutorials/java-util-concurrent/scheduledexecutorservice.html">ScheduledExecutorService</a></li><li><a href="/tutorials/java-util-concurrent/java-fork-and-join-forkjoinpool.html">Java Fork and Join using ForkJoinPool</a></li><li><a href="/tutorials/java-util-concurrent/lock.html">Java Lock</a></li><li><a href="/tutorials/java-util-concurrent/readwritelock.html">ReadWriteLock</a></li><li><a href="/tutorials/java-util-concurrent/atomicboolean.html">AtomicBoolean</a></li><li><a href="/tutorials/java-util-concurrent/atomicinteger.html">AtomicInteger</a></li><li><a href="/tutorials/java-util-concurrent/atomiclong.html">AtomicLong</a></li><li><a href="/tutorials/java-util-concurrent/atomicreference.html">AtomicReference</a></li><li><a href="/tutorials/java-util-concurrent/atomicstampedreference.html">AtomicStampedReference</a></li><li><a href="/tutorials/java-util-concurrent/atomicintegerarray.html">AtomicIntegerArray</a></li><li><a href="/tutorials/java-util-concurrent/atomiclongarray.html">AtomicLongArray</a></li><li><a href="/tutorials/java-util-concurrent/atomicreferencearray.html">AtomicReferenceArray</a></li></ol>
        </div>
    </div>
    <div id="main">

        <h1 style="text-align: left;">Java BlockingQueue</h1>

        <div id="pageToc" itemscope itemtype="http://schema.org/SiteNavigationElement">
            <ul><li><a href="#java-blockingqueue-tutorial-video">Java BlockingQueue Tutorial Video</a></li><li><a href="#blockingqueue-implementations">BlockingQueue Implementations</a></li><li><a href="#blockingqueue-usage">BlockingQueue Usage</a></li><li><a href="#java-blockingqueue-example">Java BlockingQueue Example</a></li><li><a href="#add">add()</a></li><li><a href="#offer">offer()</a></li><li><a href="#offer-timeout">offer(long millis, TimeUnit timeUnit)</a></li><li><a href="#put">put()</a></li><li><a href="#take">take()</a></li><li><a href="#poll">poll()</a></li><li><a href="#poll">poll(long timeMillis, TimeUnit timeUnit)</a></li><li><a href="#remove">remove(Object o)</a></li><li><a href="#peek">peek()</a></li><li><a href="#element">element()</a></li><li><a href="#contains">contains(Object o)</a></li><li><a href="#drainto">drainTo(Collection dest)</a></li><li><a href="#draintomax">drainTo(Collection dest, int maxElements)</a></li><li><a href="#size">size()</a></li><li><a href="#remainingCapacity">remainingCapacity</a></li></ul>
        </div>

        <div id="lastUpdate">
            <table><tr><td class="authorPhoto"></td>
                <td><p style="margin: 0px 0px 6px 0px;">
                    Jakob Jenkov<br>
                    Last update: 2021-03-22
                    </p>
                    <div class="authorSocialLinks"></div>
                </td>
            </tr>
            </table>
        </div>

        <div id="mainBody">
            <p>
    The <em>Java</em> <em>BlockingQueue</em> interface, <code>java.util.concurrent.BlockingQueue</code>,
    represents a queue which is thread safe to put elements into, and take elements out of from. In other words,
    multiple threads can be inserting and taking elements concurrently from a Java <code>BlockingQueue</code>,
    without any concurrency issues arising.
</p>

<p>
    The term <em>blocking</em> <em>queue</em> comes from the fact that
    the Java <code>BlockingQueue</code> is capable of blocking the threads that try to insert or take elements from the
    queue. For instance, if a thread tries to take an element and there are none left in the queue, the thread can
    be blocked until there is an element to take. Whether or not the calling thread is blocked depends on what
    methods you call on the <code>BlockingQueue</code>. The different methods are explained in more detail later.
</p>

<p>
    This text will not discuss how to implement a <code>BlockingQueue</code> in Java yourself. If you are interested
    in that, I have a text on <a href="/java-concurrency/blocking-queues.html">Blocking Queues</a> in my
    more theoretical <a href="/java-concurrency/index.html">Java Concurrency Tutorial</a>.
</p>



<a name="java-blockingqueue-tutorial-video"></a>
<h2>Java BlockingQueue Tutorial Video</h2>
<p>
    If you prefer video, I have a video version of this tutorial here:
    <a title="Java BlockingQueue Tutorial Video" target="_blank"  href="https://www.youtube.com/watch?v=d3xb1Nj88pw&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=16">Java BlockingQueue Tutorial</a>
</p>

<a title="Java BlockingQueue Tutorial Video" target="_blank"  href="https://www.youtube.com/watch?v=d3xb1Nj88pw&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=16"><img alt="Java BlockingQueue Tutorial Video" src="/images/java-concurrency-utils/blockingqueue-video-screenshot.png"></a>



<a name="blockingqueue-implementations"></a>
<h2>BlockingQueue Implementations</h2>
<p>
    Since <code>BlockingQueue</code> is an interface, you need to use one of its implementations to use it.
    The <code>java.util.concurrent</code> package has the following implementations of the <code>BlockingQueue</code>
    interface:
</p>

<ul>
    <li><a href="arrayblockingqueue.html">ArrayBlockingQueue</a></li>
    <li><a href="delayqueue.html">DelayQueue</a></li>
    <li><a href="linkedblockingqueue.html">LinkedBlockingQueue</a></li>
    <li>LinkedBlockingDeque</li>
    <li>LinkedTransferQueue</li>
    <li><a href="priorityblockingqueue.html">PriorityBlockingQueue</a></li>
    <li><a href="synchronousqueue.html">SynchronousQueue</a></li>
</ul>



<p>
    Click the links in the list to read more about each implementation.
</p>



<a name="blockingqueue-usage"></a>
<h2>BlockingQueue Usage</h2>

<p>
    A <code>BlockingQueue</code> is typically used to have one thread produce objects, which
    another thread consumes. Here is a diagram that illustrates this principle:
</p>

<table style="padding:10px;" align="center">
    <tr><td align="center"><img src="/images/java-concurrency-utils/blocking-queue.png" alt="A BlockingQueue with one thread putting into it, and another thread taking from it." /></td></tr>
    <tr><td align="center"><b>A BlockingQueue with one thread putting into it, and another thread taking from it.</b></td></tr>
</table>

<p>
    The producing thread will keep producing new objects and insert them into the <code>BlockingQueue</code>,
    until the queue reaches some upper bound on what it can contain. It's limit, in other words.
    If the blocking queue reaches its upper limit, the producing thread is blocked while
    trying to insert the new object. It remains blocked until a consuming thread takes an
    object out of the queue.
</p>

<p>
    The consuming thread keeps taking objects out of the <code>BlockingQueue</code> to processes them.
    If the consuming thread tries to take an object out of an empty queue, the consuming thread is
    blocked until a producing thread puts an object into the queue.
</p>



<br/>
<h3>BlockingQueue Methods</h3>
<p>
    The Java <code>BlockingQueue</code> interface has 4 different sets of methods for inserting, removing and examining
    the elements in the queue. Each set of methods behaves differently in case the requested operation
    cannot be carried out immediately. Here is a table of the methods:
</p>

<table cellspacing="0" cellpadding="5" class="dataTable" border="1">
    <tr>
        <td>&nbsp;</td>
        <td><b>Throws Exception</b></td>
        <td><b>Special Value</b></td>
        <td><b>Blocks</b></td>
        <td><b>Times Out</b></td>
    </tr>

    <tr>
        <td><b>Insert</b></td>
        <td><code>add(o)</code></td>
        <td><code>offer(o)</code></td>
        <td><code>put(o)</code></td>
        <td><code>offer(o, timeout, timeunit)</code></td>
    </tr>
    <tr>
        <td><b>Remove</b></td>
        <td><code>remove(o)</code></td>
        <td><code>poll()</code></td>
        <td><code>take()</code></td>
        <td><code>poll(timeout, timeunit)</code></td>
    </tr>
    <tr>
        <td><b>Examine</b></td>
        <td><code>element()</code></td>
        <td><code>peek()</code></td>
        <td><code>&nbsp;</code></td>
        <td><code>&nbsp;</code></td>
    </tr>
</table>

<p>
    The 4 different sets of behaviour means this:
</p>

<ol>
    <li><b>Throws Exception</b>: <br/>If the attempted operation is not possible immediately, an exception is thrown.</li>

    <li><b>Special Value</b>: <br/>If the attempted operation is not possible immediately, a special value is returned (often true / false).</li>

    <li><b>Blocks</b>: <br/>If the attempted operation is not possible immedidately, the method call blocks until it is.</li>

    <li><b>Times Out</b>: <br/>If the attempted operation is not possible immedidately, the method call blocks until it is, but waits no longer
        than the given timeout. Returns a special value telling whether the operation succeeded or not (typically true / false). </li>
</ol>


<p>
    It is not possible to insert <code>null</code> into a <code>BlockingQueue</code>. If you try to insert null, the
    <code>BlockingQueue</code> will throw a <code>NullPointerException</code>.
</p>

<p>
    It is also possible to access all the elements inside a <code>BlockingQueue</code>, and not just the elements at the start and end.
    For instance, say you have queued an object for processing, but your application decides to cancel it. You can then
    call e.g. <code>remove(o)</code> to remove a specific object in the queue. However, this is not done very efficiently,
    so you should not use these <code>Collection</code> methods unless you really have to.
</p>



<a name="java-blockingqueue-example"></a>
<h2>Java BlockingQueue  Example</h2>
<p>
    Here is a Java <code>BlockingQueue</code> example. The example uses the <code>ArrayBlockingQueue</code>
    implementation of the <code>BlockingQueue</code> interface.
</p>

<p>
    First, the <code>BlockingQueueExample</code> class which starts a <code>Producer</code> and a <code>Consumer</code>
    in separate threads. The <code>Producer</code> inserts strings into a shared <code>BlockingQueue</code>,
    and the <code>Consumer</code> takes them out.
</p>

<pre class="codeBox">
public class BlockingQueueExample {

    public static void main(String[] args) throws Exception {

        BlockingQueue queue = new ArrayBlockingQueue(1024);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);
    }
}
</pre>

<p>
    Here is the <code>Producer</code> class. Notice how it sleeps a second between each <code>put()</code> call.
    This will cause the <code>Consumer</code> to block, while waiting for objects in the queue.
</p>

<pre class="codeBox">
public class Producer implements Runnable{

    protected BlockingQueue queue = null;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(1000);
            queue.put("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
</pre>

<p>
    Here is the <code>Consumer</code> class. It just takes out the objects from the queue,
    and prints them to <code>System.out</code>.
</p>

<pre class="codeBox">
public class Consumer implements Runnable{

    protected BlockingQueue queue = null;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
</pre>



<a name="add"></a>
<h2>add()</h2>

<p>
    The Java BlockingQueue <code>add()</code> method will add the element passed as parameter to this method
    if the BlockingQueue has space for it internally. If the BlockingQueue does not have space internally for this
    new element, the <code>add()</code> method throws an IllegalStateException.
</p>



<a name="offer"></a>
<h2>offer()</h2>

<p>
    The BlockingQueue <code>offer()</code> method will add the element passed as parameter to this method
    if the BlockingQueue has space for it internally. If the BlockingQueue does not have space internally for this
    new element, the <code>offer()</code> method return <code>false</code>.
</p>



<a name="offer-timeout"></a>
<h2>offer(long millis, TimeUnit timeUnit)</h2>

<p>
    The BlockingQueue <code>offer()</code> method exists in a version which takes a time out as parameter. This
    version of the <code>offer()</code> method will add the element passed as parameter if the BlockingQueue
    has space for it internally, or space becomes available. If the BlockingQueue does not have or get
    space internally for this new element within the time out, this version of the <code>offer()</code>
    method returns <code>false</code>.
</p>



<a name="put"></a>
<h2>put()</h2>

<p>
    The BlockingQueue <code>put()</code> method inserts the element into the BlockingQueue if it has space for
    it internally. If the BlockingQueue does not have space for the new element, the <code>put()</code>
    method will block the thread calling the <code>put()</code> method until the BlockingQueue as space
    internally for the new element.
</p>


<a name="take"></a>
<h2>take()</h2>

<p>
    The Java BlockingQueue <code>take()</code> method will remove the first element in the BlockingQueue.
    If the BlockingQueue does not contain any elements, the <code>take()</code> method will block the
    thread calling <code>take()</code> until an element is inserted into the BlockingQueue.
</p>


<a name="poll"></a>
<h2>poll()</h2>

<p>
    The BlockingQueue <code>poll()</code> method will remove the first element in the BlockingQueue.
    If the BlockingQueue does not contain any elements, the <code>poll()</code> method will return <code>null</code>.
</p>




<a name="poll"></a>
<h2>poll(long timeMillis, TimeUnit timeUnit)</h2>

<p>
    The BlockingQueue <code>poll(long timeMillis, TimeUnit timeUnit)</code> method will remove the first element in the
    BlockingQueue. If the BlockingQueue does not contain any elements, this version of the <code>poll()</code> method
    will wait for an element to become available for the given amount of time passed to it as parameter. If no element
    becomes available within the given time out period, this method returns <code>null</code>.
</p>



<a name="remove"></a>
<h2>remove(Object o)</h2>

<p>
    The BlockingQueue <code>remove(Object o)</code> method will remove a single instance of the given element from the
    lockingQueue, if that element exists in the BlockingQueue. The <code>remove()</code> method will use the
    <code>o.equals(element)</code> to decide if the object o passed as parameter matches a given element in the
    BlockingQueue. If the BlockingQueue contains more than one element matching the given o parameter, only
    one of these elements will be removed from the BlockingQueue. The <code>remove()</code> method will return
    <code>true</code> if an element was removed, and <code>false</code> if not.
</p>



<a name="peek"></a>
<h2>peek()</h2>
<p>
    The BlockingQueue <code>peek()</code> method will return the first element of the BlockingQueue without removing it.
    If the BlockingQueue does not contain any elements, the <code>peek()</code> method will return <code>null</code>.
</p>



<a name="element"></a>
<h2>element()</h2>
<p>
    The BlockingQueue <code>element()</code> method will return the first element of the BlockingQueue without removing it.
    If the BlockingQueue does not contain any elements, the <code>element()</code> method will throw a
    NoSuchElementException.
</p>



<a name="contains"></a>
<h2>contains(Object o)</h2>

<p>
    The BlockingQueue <code>contains(Object o)</code> method will return <code>true</code> if the BlockingQueue
    contains an object matching the object passed as parameter to the <code>contains()</code> method.
    The <code>Objects.equals(o, element)</code> statement is used to check if the parameter object o matches a given
    element in the BlockingQueue. If an element is found matching the parameter object, this method returns
    <code>true</code>. If no matching element is found, <code>false</code> is returned.
</p>



<a name="drainto"></a>
<h2>drainTo(Collection dest)</h2>

<p>
    The <code>drainTo(Collection dest)</code> method drains all the elements of the BlockingQueue into the given destination
    Collection.
</p>



<a name="draintomax"></a>
<h2>drainTo(Collection dest, int maxElements)</h2>

<p>
    The <code>drainTo(Collection dest, int maxElements)</code> drains up to <code>maxElements</code> from the
    BlockingQueue into the destination Collection.
</p>




<a name="size"></a>
<h2>size()</h2>

<p>
    The BlockingQueue <code>size()</code> method returns the number of elements stored in BlockingQueue.
</p>



<a name="remainingCapacity"></a>
<h2>remainingCapacity</h2>

<p>
    The BlockingQueue <code>remainingCapacity()</code> method returns the remaining (unused) capacity of
    the BlockingQueue. The remaining capacity is calculated as full capacity minus the number of elements
    stored in the BlockingQueue.
</p>













        </div>

        <div id="next">Next: <a href="/tutorials/java-util-concurrent/arrayblockingqueue.html">ArrayBlockingQueue</a></div>
        <div id="bottomSocial">

            <div style="display:inline-block;">
                <table>
                    <tr><td colspan="2">
                        <a href='https://twitter.com/intent/tweet?url=http://tutorials.jenkov.com/tutorials/java-util-concurrent/blockingqueue.html&original_referer=jjenkov' class='twitter-share-button' data-via='jjenkov' target="_blank">Tweet</a>
                    </td></tr>
                    <tr><td class="authorPhoto"></td><td><p style="margin: 0px 0px 6px 0px;">Jakob Jenkov</p><div class="authorSocialLinks"></div></td></tr>
                </table>
            </div>
            <div  style="display: none;" class="newsletterForm"  style="display:inline-block;"></div>
        </div>
    </div>

    <div id="rightWrapper">
        <b>Featured Videos</b><br><br>



        <a target="_blank" href="https://www.youtube.com/watch?v=kirhhcFAGB4&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=3"><img src="/images/java-concurrency/java-virtual-threads-video-screenshot-small.jpg"></a><br><br>
        <a target="_blank" href="https://www.youtube.com/watch?v=EgOjklzlnFw&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=23"><img src="/images/java-concurrency/java-thread-signaling-video-screenshot-small.png"></a><br><br>
        <a target="_blank" href="https://www.youtube.com/watch?v=ULlFWomaPVw"><img src="/images/dev-essentials/software-performance-optimization-principles-video-screenshot-small.png" alt="Core Software Performance Optimization Principles"></a><br><br>
        <a target="_blank" href="https://www.youtube.com/watch?v=DqpPRxCmxrM&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=22"><img src="/images/java-concurrency/thread-congestion-video-screenshot-small.png" alt="Thread Congestion in Java - Video Tutorial"></a><br><br>
        <a target="_blank" href="https://www.youtube.com/watch?v=QrYIOs1dA3M&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=21"><img src="/images/java-concurrency/single-threaded-design-video-screenshot-small.png"></a><br><br>
        <a target="_blank" href="https://www.youtube.com/watch?v=csXvBjBOHws"><img src="/images/polymorph/polymorph-video-screenshot-small.png"></a><br><br>
        <a target="_blank" href="https://www.youtube.com/watch?v=neaGeFZ2j_w"><img src="/images/java/what-I-love-about-Java-video-screenshot-small.png"></a><br><br>
        <a target="_blank" href="https://www.youtube.com/watch?v=tLS85IfsbYE&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=20"><img src="/images/java-concurrency/false-sharing-video-screenshot-small.png"></a><br><br>
        <!--<a target="_blank" href="https://www.youtube.com/watch?v=nNXkzDS6dOg&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=7"><img src="/images/java-concurrency/java-concurrency-plus-cache-coherence-video-screenshot-small.png"></a><br><br>-->
        <!-- <a target="_blank" href="https://www.youtube.com/watch?v=ufWVK7CHOAk&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=18"><img src="/images/java-concurrency/compare-and-swap-video-screenshot-small.png"></a><br><br> -->
        <a target="_blank" href="https://www.youtube.com/watch?v=mTGdtC9f4EU&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=1"><img src="/images/java-concurrency/java-concurrency-video-screenshot-small.jpg"></a><br><br>

        <br/>
        <b>Advertisements</b>
        <br/>
        <a target="_blank" href="https://vladmihalcea.teachable.com/p/high-performance-java-persistence-ebook?affcode=172599_7e3dmn34"><img src="/images/vladmihalcea/hpjp-covers-small.jpg"></a>

    </div>
</div>

<div id="footer">
    Copyright Jenkov Aps
</div>

<script src="/nav.js"></script>
<script>
nav.select(5, 3, 1);
nav.generateNavPath();
nav.generateNavMenu();
</script>


<style>
.navButton{color: #ffffff; font-weight: bold;}
#trailTocFixedDiv{position: fixed;  top : 0px; left : 0px; width : 100%; height : 100%;  display : none; background-color: #ffffff;}
#trailTocFixedInnerDiv{width: 400px; max-width: 94%; height: calc(100% - 72px); overflow : auto; margin: 20px auto 20px auto; padding: 0px 0px 180px 0px;}
#trailTocFixedInnerDiv th{border-bottom: 1px solid #f0f0f0;padding: 6px 10px;text-align: left;font-size: 1.2em;}
#trailTocFixedInnerDiv td{border-bottom: 1px solid #f0f0f0;padding: 6px 20px;vertical-align: top;}
#trailTocFixedCloseButton{width : 72px; padding: 10px 20px; background-color: #009900; border: 2px solid #006600; color: #ffffff; font-size: 0.8em; font-weight: bold; position: absolute; right: 0px; cursor: pointer; box-shadow: 2px 2px 2px 2px #cccccc;}
#bottomNavBarDiv{position:fixed; bottom: 0px; width: 100%;  background-color: #202020; border-top: 1px solid #444444;}
.buttonNavBarButtonDivActive{background-color: #404040;}
#bottomNavBarRow>div{border-left: 1px solid #404040; border-right: 1px solid #000000;  height: 48px;  padding-top:16px; text-align: center; cursor: pointer;}
#bottomNavBarDiv a{color: #ffffff; font-weight: bold;}
#bottomNavBarDiv br{display:none;}
#bottomNavBarDiv img{ height: 20px; }
#nextButton2{ display:none; }
.dataTable{border-top: 1px solid #cccccc;border-right: 1px solid #cccccc; border-spacing: 0px; }
.dataTable th{ padding: 4px 8px; border-left: 1px solid #cccccc; border-bottom: 1px solid #cccccc; background-color: #eeeeee; }
.dataTable td{ padding: 4px 8px; border-left: 1px solid #cccccc; border-bottom: 1px solid #cccccc; }
@media only screen and (max-width: 700px){
  #bottomNavBarDiv, #bottomNavBarDiv [jqc-cell] { height: 58px; }
  #bottomNavBarDiv [jqc-cell] {padding-top: 12px;}
  #bottomNavBarDiv a { font-size: 0.8em; }
  #bottomNavBarDiv br { display: inline; }
  #nextButton1 { display: none; }
  #nextButton2 { display: inline; }
}
@media only screen and (max-width: 400px){
  #bottomNavBarDiv [jqc-cell] {padding-top: 10px;}
  #bottomNavBarDiv a {font-size: 0.5em;}
  #bottomNavBarDiv img {height: 24px;}
}
</style>
<style>
#bottomNavBar2Parent{position:fixed; bottom: 0px; width: 100%;  background-color: #202020; border-top: 1px solid #444444;}
#bottomNavBar2>div{border-left: 1px solid #404040; border-right: 1px solid #000000;  height: 48px;  padding-top:16px; text-align: center; cursor: pointer;}


#bottomNavBar2{
  display: grid;
}
#allTrailsButtonDiv2{
  grid-column-start: 1;
  grid-column-end: 3;
}

#trailTocButtonDiv2 {
  grid-column-start: 3;
  grid-column-end: 5;
}

#pageTocButtonDiv2{
  grid-column-start: 5;
  grid-column-end: 7;
}

#prevButtonDiv2 {
  grid-column-start: 7;
  grid-column-end: 9;
}

#nextButtonDiv2 {
  grid-column-start: 9;
  grid-column-end: 13;
}
</style>

<div id="trailTocFixedDiv">
    <div id="trailTocFixedCloseButton">Close TOC</div>
    <div id="trailTocFixedInnerDiv"></div>
</div>

<div id="bottomNavBar2Parent">
    <div id="bottomNavBar2">

        <div id="allTrailsButtonDiv2">
            <span><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAbUlEQVRIx+3SQQrAIAxEUenNewrvmkXUQgVJEwWdQEED4kb+QzSEPw0z389yi6d3JJKaWY5ryBKgxSVS9jgF9OItQkRXRaBxDYHHJeISt34XND5EEHETQcY/iEfcvAkqbD7yAaYB1PkD7AB4TAY0StbUr69awQAAAABJRU5ErkJggg==" ></span> <br>
            <span class="navButton" style="position:relative; top:-4px;">All Trails</span>
        </div>

        <div id="trailTocButtonDiv2">
            <span><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAPElEQVRIx2P4TwPAgAzoZgEDrQDdLaB6cI1aMHgtoFriGRkWEB3eg9YHo0E08BaM8CAiWoLaFlAL0NwCANELs4XjmhvVAAAAAElFTkSuQmCC"></span> <br>
            <span class="navButton" style="position:relative; top:-4px;">Trail TOC</span>
        </div>

        <div id="pageTocButtonDiv2">
            <span><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAANUlEQVRIx2NggIL/VAYM6GDUglELqGcBA4Vg1ALqWkBOhA4uH4wG0cBbMBpEw9QCagG6WQAAX980BcIgiscAAAAASUVORK5CYII=" ></span> <br>
            <span class="navButton" style="position:relative; top:-4px;">Page TOC</span>
        </div>

        <div id="prevButtonDiv2">
            <span><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjAuNvyMY98AAACCSURBVEhL3ZRRCsAgDEM9iez+dxJPUl0K9W+MtKMiC/RL80Lz0eKViNQxRpsvwnvHv8ssvGD8ORzjh7Odf4F34zxK30O1qImEV7Pw0nUJeEuFY/xwspZceKiWVLgqPUC1JQSAc0Iwh2+iZiIkdiqWdobknOslmM8JwcbxELaujSGl3GNH6mWcCMu2AAAAAElFTkSuQmCC"></span> <br>
            <span class="navButton" style="position:relative; top:-4px;">Previous</span>
        </div>

        <div id="nextButtonDiv2">
            <!--<span class="navButton" id="nextButton1_2" style="position:relative; top:-4px;">Next</span>-->
            <span><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAb0lEQVRIx82W2w1AIQhDmcS4/07GScDbu4AvUlMSv9QeA6RohnD3GhF9TAL7DeeK3cYuBEscgss6kBc16VlI2YTULKStIH9aMzXRgTxJlzaECpAQv+okaptSX35gFed+RBWn2jV14FBHJvVnwRT/AGJL6mWb6M7pAAAAAElFTkSuQmCC" ></span> <br>
            <span class="navButton" id="nextButton2_2" style="position:relative; top:-4px;">Next</span>
        </div>

    </div>
</div>

<!-- Top menu script -->
<script>
var state = {
    menuVisible: false
}

event("topBarLogo", "mouseover", function() { console.log("mouse over topBarLogo"); } );
event("logoLink"  , "click", function() { window.location.href = "/"; } );
event("menuButton", "mouseover", function() { console.log("mouse over menuButton"); } );
event("menuButton", "click", function() { toggleMenu(); } );

function toggleMenu() {
    if(state.menuVisible) {
        document.getElementById("menuItems").style.display = "none";
        state.menuVisible = false;
    } else {
        document.getElementById("menuItems").style.display = "block";
        state.menuVisible = true;
    }
}

function event(elId, eventId, f) {
    document.getElementById(elId).addEventListener(eventId, function(e) { f(); e.stopPropagation(); });
}
</script>


<!-- Nav bar code -->
<script>
var prevArticleInCategory = "/tutorials/java-util-concurrent/index.html";
var nextArticleInCategory = "/tutorials/java-util-concurrent/arrayblockingqueue.html";

function toggle(el){
    if(el.isShown == null || el.isShown == false) {
        el.isShown = true;
        el.style.display = "block";
    } else {
        el.isShown = false;
        el.style.display = "none";
    }
}

qid("allTrailsButtonDiv2").addEventListener("mouseup", function(e) {
    location.href="/";
    e.preventDefault();
    e.stopPropagation();
});
qid("trailTocButtonDiv2").addEventListener("mouseup", function(e) {
    var tocHtml = qid("trailToc").innerHTML;
    qid("trailTocFixedInnerDiv").innerHTML = tocHtml + "<br><br><br><br><br><br><br><br><br><br>";
    toggle(qid("trailTocFixedDiv"));
    e.preventDefault();
    e.stopPropagation();
});
qid("trailTocFixedCloseButton").addEventListener("mouseup", function(e) {
    toggle(qid("trailTocFixedDiv"));
    e.preventDefault();
    e.stopPropagation();
});
qid("pageTocButtonDiv2").addEventListener("mouseup", function(e) {
    location.href="#pageToc";
    e.preventDefault();
    e.stopPropagation();
});
qid("prevButtonDiv2").addEventListener("mouseup", function(e) {
    if(prevArticleInCategory != "") {
        location.href = prevArticleInCategory;
    } else {
        alert("This is the first article in this trail");
    }
    e.preventDefault();
    e.stopPropagation();
});
qid("nextButtonDiv2").addEventListener("mouseup", function(e) {
  if(nextArticleInCategory != "") {
    location.href= nextArticleInCategory;
  } else {
    alert("This is the last article in this trail.");
  }
  e.preventDefault();
  e.stopPropagation();
});
</script>



<script>
function insertByCssClass(cssClass, html) {
    var socialEls = document.getElementsByClassName(cssClass);

    for(var i=0; i<socialEls.length; i++){
        socialEls[i].innerHTML = html;
    }
}
function soMeLink(href, title, src) { return '<a class="iconLink" href="'+href+'" title="'+title+'" target="_blank"><img src="'+src+'"></a> '; }

var soMeLinks =
  soMeLink("https://twitter.com/#!/jjenkov", "Follow on Twitter", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAABpElEQVRIx2NgwAOUlZWVFBUVqxQUFPYC8XMg/gvFIPZekBxIDQOpQAkIgAashhr2nwAGqVkN0kOU4UBXxQA1fCbCYHT8GaSXkOFlZBiMgkFm4HM5RYYjWYLqE2iYkxMsOIMLJU6gEUqqIaCUdB1PQlgNT4rYFAG9uRVXqgHKpejp6TFBfa8BFJsPpEHx9xtZHTgJQ9M5hkEqKipyQLkkIPs7mtxM9PizsLBgAloQBZR7jObIKgZoJsKwAKjBCOpCNSB/KcwioKYIbIkEKLceizl7GaBhiS0lLNXS0mKCGaCmpsYDtMwO6G05HBZgSyTPGXBFEtAgI3V1dS5iMqeqqqoIrvjCaQEQzyW2aAH6NgSfBc9xSILioQIYTByELACq3Y4rKeOMZGg81BEyHOgIFzx5ZS/OZIqEF4LiA4fhckD5p3gcWIUro4GS5FsgPg5UVASMRC4shpsA5R/iK8bhdQWWogJkwUSgIXrIhurr64MylBkoAaDlWtxFBRGF3Xsgvgwtd4gtED9jVEA0La6RLKFdhYPmE9pUmWhxQptKHxlQo9kCAHsFA8aGHksTAAAAAElFTkSuQmCC")
+ soMeLink("http://www.linkedin.com/pub/jakob-jenkov/0/a8/4a3", "Connect on LinkedIn", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAABEklEQVRIx2NgwAOUlZWVFBUVqxQUFPYC8XMg/gvFIPZekBxIDQOpQAkIgAashhr2nwAGqVkN0kOU4UBXxQA1fCbCYHT8GaSXkOFlZBiMgkFm4HM5RYYjWYLqE2iYkxMsOIMLJU6gEYpL8XcoJtWS1fCkiCe1fAfKy4AwGZb8BSdhaDr/TwMLQHFRxQDNRP9pEEQgvJcBmitxKkKKp/9IKa5bTU2NT0VFRQoovh+P/ucMhHIrNguABosAg00MmgJ18MUDWRYA2beB+D6IbW1tzUTIApKDCCbX0tLChMzHFUR7aWjBXkLJlCILwMmUQEajBP+F1xUEigpy8Wr6FXY0L67pUuHQpcpEixPaVPrIgBrNFgAzO0iIN1MJVQAAAABJRU5ErkJggg==")
+ soMeLink("https://www.youtube.com/user/jjenkov", "Subscribe on YouTube", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAABNElEQVRIx7VWSwrCMBANxYW48gTS2kJFaBfiHdq1uxbP4cJNz+K6eIQeR3oAlyI6T0Zpwlj7MYEHJZN5k8zMS6pUy/B9f+l53tF13YpQE+4MfFewYY3qO5Y0iKBksscPYE0Jn07ktKs9OVw7EJu4wvcX+WEAsQZwtO18FHkjiH4SzvmQtHxNl1YTLui/yN8oP60odMuFjrkLgmARhuE8juNpkiSToigcAN+Ygw1rsBY+Zne9Wpj73Mxh3re14SPwHBWLSDNgV4Lo3LYA8BHSVClWpWaIomhmEtD8jXCi2q2lAPARAtRKUmue544QoKncMwXaNu1ZljmSyscE2HQN0DdFq74psltk623aVWhpmg4TGufX3lWBYf2y4xzau64bQew9OMZJ7DyZRk3sPPqGuEb/tjwB4iHvG88CuqcAAAAASUVORK5CYII=")
+ soMeLink("http://jenkov.com/rss.xml", "Subscribe to RSS Feed", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAABqUlEQVRIx71WwUrDQBANEqQnP6AUSZo0JDnkByLSU/HkB0g/o0jJVUrx5MGvkHxDzuKxZ/HgoeQsOXiQom9kVjbJZhON7cKDbDI7M/vezG4MQzMcxxnbtp1YlpUBObBj0HNG38jG+O0YY8BBys4+W0A2Ka3p5BxZzbGg6OC4ioLWtjm//oPjEsiHLvNezqUg5Z0w5zVa4jg2XdcdYsEM81vgtStdJU1Y0JphdZdBEJgIdtUxUPpTippquYfDJZKZknMRCLs6wbeHtur6LmGu8y7bzmG78DzvWNLtrkWLxOAmUhog01PKHs8rYMvvNzK/LTvJDO5KpYHM/2QyGSCjNdO5FUGYriZNckPXrcQ/VZDv+6ZEy5zXbARdLLxSB20ACc9wciYFWXMCC5qHYXjUsIudliLgA3gC3oB3EYToYk1yUV3cJ0qKGkUGzZdcyiMO8iLoQrAbtpnyfKYUWVemURQNpGp5ZFouuPvPhU4s9lBZprpGg8GKgtBOmC56n7DDkWhGmtOxomw03VHRE2nrYdcDRe0C2utxfZAL5yBXZkWT/Vz68viP35Yv1q4mS+AFuV8AAAAASUVORK5CYII=")
+ soMeLink("https://t.me/jenkov_com", "Subscribe to Telegram Channel", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAACXBIWXMAAA7EAAAOxAGVKw4bAAABv0lEQVRIx71WPUsDQRB9bya7FyQEI0QsJaSwsLKwFAsh/gArC/FnWARBUoullYWIiBxibRFSWVhbilhYBSuRVCbEIhs5l9vLaYgD09wy7+2++Togw1S1TrIJoA2gC2DgvAugTbKpqnX81lS1BiB2YMMJPgAQu5jJRnIPQC8HsO89F5sJfvAH4B/uMII3nwo8QfLzJU6/v8gSlMvPSTwF2AvJFoA773s8vn09Z7Uk/QPAuYhsVioVcZe89KtLVetwdZ4XuENy31pbKhaLVkR2Eyo8peSiCYyaKAv0meShqi6PkURkneSNiNQAwFq7EIhtA6Ou9A/eAZyJyEa1Wh1LAGNMieQJgAdVXUwQbgUIukjRP7bWzvllLCLbAF4A3BUKhVLO/hmkEfQAXJPcNcbMG2NK4wSSvIyiyKa00W0WQRdh/T9VdQ2jMXBcLpcF6faaJVFmkkkekbwIAMMYs5QR3xaSHWTYcDjcUdVTl4cVEVlJnvf7/fVQLMlOrkZT1VURWQPw5uTabzQa4kBaIf2Tu2LSqHjEqHST366iKFoEcB+Iib+f8h/DbrbjOkEyu4XjvWQ2K9PLyWyWvkc09W/LFx++dNA/GYlaAAAAAElFTkSuQmCC")
  ;

insertByCssClass("authorSocialLinks", soMeLinks);

var authorPhoto = "<img src='data:image/jpeg;base64,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' alt='Jakob Jenkov' />"

insertByCssClass("authorPhoto", authorPhoto);
</script>


<!-- Google Analytics Script -->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  //ga('create', 'UA-4036229-1', 'auto');
  ga('create', 'UA-4036229-1', { 'storage': 'none' });
  ga('send', 'pageview');
</script>

</body>
</html>
