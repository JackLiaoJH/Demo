#### 1、Path对象

    在代码中我们看到了我们新建了一个Paint画笔对象，对于画笔对象，它有很多设置属性的：
    void  setARGB(int a, int r, int g, int b)  设置Paint对象颜色，参数一为alpha透明通道
    void  setAlpha(int a)  设置alpha不透明度，范围为0~255
    void  setAntiAlias(boolean aa)  //是否抗锯齿，默认值是false
    void  setColor(int color)  //设置颜色，这里Android内部定义的有Color类包含了一些常见颜色定义
    void  setFakeBoldText(boolean fakeBoldText)  //设置伪粗体文本
    void  setLinearText(boolean linearText)  //设置线性文本
    PathEffect  setPathEffect(PathEffect effect)  //设置路径效果
    Rasterizer  setRasterizer(Rasterizer rasterizer) //设置光栅化
    Shader  setShader(Shader shader)  //设置阴影 ,我们在后面会详细说一下Shader对象的
    void  setTextAlign(Paint.Align align)  //设置文本对齐
    void  setTextScaleX(float scaleX)  //设置文本缩放倍数，1.0f为原始
    void  setTextSize(float textSize)  //设置字体大小
    Typeface  setTypeface(Typeface typeface)  //设置字体，Typeface包含了字体的类型，粗细，还有倾斜、颜色等
    注：
        Paint mp = new paint();
        mp.setTypeface(Typeface.DEFAULT_BOLD)

        常用的字体类型名称还有：
        Typeface.DEFAULT //常规字体类型
        Typeface.DEFAULT_BOLD //黑体字体类型
        Typeface.MONOSPACE //等宽字体类型
        Typeface.SANS_SERIF //sans serif字体类型
        Typeface.SERIF //serif字体类型

        除了字体类型设置之外，还可以为字体类型设置字体风格，如设置粗体：
        Paint mp = new Paint();
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        p.setTypeface( font );

        常用的字体风格名称还有：
        Typeface.BOLD //粗体
        Typeface.BOLD_ITALIC //粗斜体
        Typeface.ITALIC //斜体
        Typeface.NORMAL //常规
    void  setUnderlineText(boolean underlineText)  //设置下划线
    void  setStyle(Style style) //设置画笔样式
   > 注：
        常用的样式
        Paint.Style.FILL
        Paint.Style.STROKE
        Paint.Style.FILL_AND_STROKE
        这里的FILL和STROKE两种方式用的最多，他们的区别也很好理解的，FILL就是填充的意思，STROKE就是空心的意思，只有	图形的轮廓形状，内部是空的。

    void setStrokeWidth(float width) //在画笔的样式为STROKE的时候，图形的轮廓宽度

### Canvas

    画贝塞尔曲线(drawPath)
        这种曲线其实我们在开发过程中很少用到，不过在图形学中绘制贝塞尔曲线的时候，我们需要的要素是：起始点+控制点+终点
        [java] view plaincopy在CODE上查看代码片派生到我的代码片
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, p);//画出贝塞尔曲线
        它也是使用Path对象的。不过用的是quadTo方法
        参数一：控制点的x坐标
        参数二：控制点的y坐标
        参数三：终点的x坐标
        参数四：终点的y坐标
        这里需要注意的是，调用moveTo方法来确定开始坐标的，如果没有调用这个方法，那么起始点坐标默认是：(0,0)

### 颜色渲染器Shader对象
    下面再来看下一个知识点：颜色渲染Shader对象
    为什么我要把Shader对象单独拿出来说一下呢？因为这个对象在对于我们处理图形特效的时候是非常有用的
    下面来看一下Android中Shader对象
    在Android Api中关于颜色渲染的几个重要的类:
        Shader,BitmapShader,ComposeShader,LinearGradient,RadialGradient,SweepGradient
    它们之间的关系是：
        Shader是后面几个类的父类

    该类作为基类主要是返回绘制时颜色的横向跨度。其子类可以作用与Piant。通过 paint.setShader(Shader shader);来实现一些渲染效果。之作用与图形不作用与bitmap。
    构造方法为默认的构造方法。

    枚举：
        emun Shader.TileMode

    定义了平铺的3种模式：
        static final Shader.TileMode CLAMP: 边缘拉伸.
        static final Shader.TileMode MIRROR：在水平方向和垂直方向交替景象, 两个相邻图像间没有缝隙.
        Static final Shader.TillMode REPETA：在水平方向和垂直方向重复摆放,两个相邻图像间有缝隙缝隙.

    方法：
        1. boolean getLoaclMatrix(Matrix localM); 如果shader有一个非本地的矩阵将返回true.
        localM：如果不为null将被设置为shader的本地矩阵.
        2. void setLocalMatrix(Matrix localM);
        设置shader的本地矩阵,如果localM为空将重置shader的本地矩阵。

    Shader的直接子类:
        BitmapShader    : 位图图像渲染
        LinearGradient  : 线性渲染
        RadialGradient  : 环形渲染
        SweepGradient   : 扫描渐变渲染/梯度渲染
        ComposeShader   : 组合渲染，可以和其他几个子类组合起来使用
        是不是很像Animation及其子类的关系(AlphaAnimation,RotateAnimation,ScaleAnimation,TranslateAnimation, AnimationSet)
        既有具体的渲染效果，也有渲染效果的组合

    下面说下Shader的使用步骤:
        1. 构建Shader对象
        2. 通过Paint的setShader方法设置渲染对象
        3.设置渲染对象
        4.绘制时使用这个Paint对象

    那么下面就开始来介绍各个Shader的相关知识：

#### 1、BitmapShader
        public   BitmapShader(Bitmap bitmap,Shader.TileMode tileX,Shader.TileMode tileY)
            调用这个方法来产生一个画有一个位图的渲染器（Shader）。
            bitmap   在渲染器内使用的位图
            tileX      The tiling mode for x to draw the bitmap in.   在位图上X方向渲染器平铺模式
            tileY     The tiling mode for y to draw the bitmap in.    在位图上Y方向渲染器平铺模式
            TileMode：
            CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。
            REPEAT ：横向和纵向的重复渲染器图片，平铺。
            MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT重复方式不一样，他是以镜像方式平铺。
#### 2、LinearGradient

    LinearGradient有两个构造函数;
    public LinearGradient(float x0, float y0, float x1, float y1, int[] colors, float[] positions,Shader.TileMode tile)
        参数:
        float x0: 渐变起始点x坐标
        float y0:渐变起始点y坐标
        float x1:渐变结束点x坐标
        float y1:渐变结束点y坐标
        int[] colors:颜色 的int 数组
        float[] positions: 相对位置的颜色数组,可为null,  若为null,可为null,颜色沿渐变线均匀分布
        Shader.TileMode tile: 渲染器平铺模式

    public LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1,Shader.TileMode tile)
        float x0: 渐变起始点x坐标
        float y0:渐变起始点y坐标
        float x1:渐变结束点x坐标
        float y1:渐变结束点y坐标
        int color0: 起始渐变色
        int color1: 结束渐变色
        Shader.TileMode tile: 渲染器平铺模式

#### 3、RadialGradient
     圆形渲染器，这种渲染器很好理解，就是同心圆的渲染机制

     public RadialGradient(float x, float y, float radius, int[] colors, float[] positions,Shader.TileMode tile)
         float x:  圆心X坐标
         float y:  圆心Y坐标
         float radius: 半径
         int[] colors:  渲染颜色数组
         floate[] positions: 相对位置数组,可为null,  若为null,可为null,颜色沿渐变线均匀分布
         Shader.TileMode tile:渲染器平铺模式

     public RadialGradient(float x, float y, float radius, int color0, int color1,Shader.TileMode tile)
         float x:  圆心X坐标
         float y:  圆心Y坐标
         float radius: 半径
         int color0: 圆心颜色
         int color1: 圆边缘颜色
         Shader.TileMode tile:渲染器平铺模式

#### 4、SweepGradient
     梯度渲染器，或者是扇形选择器，和雷达扫描效果差不多

     public SweepGradient(float cx, float cy, int[] colors, float[] positions)
         Parameters:
         cx	渲染中心点x 坐标
         cy	渲染中心y 点坐标
         colors	围绕中心渲染的颜色数组，至少要有两种颜色值
         positions	相对位置的颜色数组,可为null,  若为null,可为null,颜色沿渐变线均匀分布

     public SweepGradient(float cx, float cy, int color0, int color1)
         Parameters:
         cx	渲染中心点x 坐标
         cy	渲染中心点y 坐标
         color0	起始渲染颜色
         color1	结束渲染颜色


### 三、自定义视图View

1、我们在自定义视图View的时候正确的步骤和方法
    1）、必须定义有Context/Attrbuite参数的构造方法，并且调用父类的方法
        public LabelView(Context context, AttributeSet attrs)

2）、重写onMeasure方法


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }
    来设置View的大小：
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.i("DEMO","measureSpec:"+Integer.toBinaryString(measureSpec));
        Log.i("DEMO","specMode:"+Integer.toBinaryString(specMode));
        Log.i("DEMO","specSize:"+Integer.toBinaryString(specSize));

        /**
         * 一般来说，自定义控件都会去重写View的onMeasure方法，因为该方法指定该控件在屏幕上的大小。
                protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
                onMeasure传入的两个参数是由上一层控件传入的大小，有多种情况，重写该方法时需要对计算控件的实际大小，然后调用setMeasuredDimension(int, int)设置实际大小。
                onMeasure传入的widthMeasureSpec和heightMeasureSpec不是一般的尺寸数值，而是将模式和尺寸组合在一起的数值。
                我们需要通过int mode = MeasureSpec.getMode(widthMeasureSpec)得到模式，用int size = MeasureSpec.getSize(widthMeasureSpec)得到尺寸。
                mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY, MeasureSpec.AT_MOST。
                MeasureSpec.EXACTLY是精确尺寸，当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，
                或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
                MeasureSpec.AT_MOST是最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，
                控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
                MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
                因此，在重写onMeasure方法时要根据模式不同进行尺寸计算。下面代码就是一种比较典型的方式：
         */

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

3）、重写onTouchEvent方法
    获取坐标，计算坐标，然后通过invalidate和postInvalidate方法进行画面的刷新操作即可
    关于这两个刷新方法的区别是：invalidate方法是在UI线程中调用的，postInvalidate可以在子线程中调用，而且最重要的是postInvalidate可以延迟调用


###### 更详细的请点击
    http://blog.csdn.net/jiangwei0910410003/article/details/42640665