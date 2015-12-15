package com.taisukeoe.scalapromise

import android.view.View
import android.view.View.OnClickListener

/**
 * Created by taisukeoe on 15/12/13.
 */
object Implicits {
  implicit class RichButtonSyntax(val underlying: View) extends AnyVal {
    def onClick(f: View => Unit): Unit = underlying.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = f(v)
    })
  }
}
