package com.taisukeoe.scalapromise

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

import scala.concurrent.{Future, Promise}
import scala.util.Try

/**
 * Created by taisukeoe on 15/12/13.
 */
trait ImageLoadable extends Activity {
  lazy val IMAGE_FETCH_ID = 12345
  private var promise: Option[Promise[Uri]] = None

  def chooseImageUri(): Future[Uri] = {
    promise.filterNot(_.isCompleted).foreach(_.failure(new InterruptedException("Asked to load another image. Aborted.")))
    val p = Promise[Uri]()
    promise = Some(p)
    val intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    intent.setType("image/*")
    startActivityForResult(intent, IMAGE_FETCH_ID)
    p.future
  }

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Unit = requestCode match {
    case IMAGE_FETCH_ID => if (resultCode == Activity.RESULT_OK)
      promise.foreach(_.complete(Try(data.getData)))
    else
      promise.foreach(_.failure(ImageNotAvailableException("Failed to fetch image.")))
    case _ => //do nothing
  }
}

case class ImageNotAvailableException(msg: String) extends IllegalArgumentException(msg)
