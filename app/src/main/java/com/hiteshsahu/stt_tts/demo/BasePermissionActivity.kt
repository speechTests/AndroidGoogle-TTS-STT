/*
 * Creator: Hitesh Sahu on 2/8/19 1:56 PM
 * Last modified: 2/8/19 1:56 PM
 * Copyright: All rights reserved Ⓒ 2019 http://hiteshsahu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file    except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.hiteshsahu.stt_tts.demo

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * Abstarct class help with permission stuffs
 */
abstract class BasePermissionActivity : AppCompatActivity() {

    private val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setUpView()
    }

    //SetUp views after permission granted
    abstract fun setUpView()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                val perms = HashMap<String, Int>()
                perms[Manifest.permission.RECORD_AUDIO] = PackageManager.PERMISSION_GRANTED

                for (i in permissions.indices)
                    perms[permissions[i]] = grantResults[i]

                if (perms[Manifest.permission.RECORD_AUDIO] == PackageManager.PERMISSION_GRANTED) {

                    // All Permissions Granted
                    setUpView()

                } else {
                    // Permission Denied
                    Toast.makeText(applicationContext, "Some Permissions are Denied Exiting App", Toast.LENGTH_SHORT)
                        .show()

                    finish()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}

