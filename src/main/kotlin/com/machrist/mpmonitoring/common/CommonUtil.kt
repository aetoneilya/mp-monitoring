package com.machrist.mpmonitoring.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <T: Any> T.logger(): Lazy<Logger> = lazyOf(LoggerFactory.getLogger(this.javaClass.name))