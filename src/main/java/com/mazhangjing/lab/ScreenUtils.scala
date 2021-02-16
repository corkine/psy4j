package com.mazhangjing.lab

import scalafx.scene.Parent


object ScreenUtils {
  def simple(showMs:Int = -1,
             name:String = "Untitled Screen",
             id:Integer = -1)(display: => Parent): BasicScreen = new ScreenAdaptor {
      override def initScreen(): BasicScreen = {
        layout = display
        duration = if (showMs == -1) UNLIMITED else showMs
        information = name
        if (id != -1) screenID = id
        this
      }
    }.initScreen()
}
