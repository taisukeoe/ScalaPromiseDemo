package com.taisukeoe.scalapromise

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.taisukeoe.scalapromise.Implicits._

import scala.concurrent.ExecutionContext

/**
 * Created by taisukeoe on 15/12/13.
 */
class MainActivity extends Activity with TypedFindView with ImageLoadable{
  lazy val uiContext = UIContext(this)
  override def onCreate(bundle: Bundle) {
    if (BuildConfig.DEBUG) Log.d("ScalaPromiseSample", "MainActivity onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.main)

    findView(TR.button_image).onClick{
     _ => chooseImageUri().foreach{
       bmp => findView(TR.image).setImageURI(bmp)
     }(uiContext)
    }
  }
}

case class UIContext(act: Activity) extends ExecutionContext {
  override def execute(runnable: Runnable): Unit = act.runOnUiThread(runnable)

  override def reportFailure(cause: Throwable): Unit = cause.printStackTrace()
}
