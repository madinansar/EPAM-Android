package com.example.browserapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.browserapp.databinding.ActivityMainBinding
import com.example.browserapp.ui.theme.BrowserAppTheme
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import androidx.core.content.ContextCompat


class MainActivity : ComponentActivity()  {
    private lateinit var webview: WebView
    private lateinit var editText: EditText

    private lateinit var binding : ActivityMainBinding
    private var isErrorOccurred = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setActionBar(toolbar)

        webview = binding.webView
        editText = binding.editText

        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                updateTitle(view)
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                isErrorOccurred = true
            }

        }

        //webview.loadUrl("https://google.com")
        webview.settings.javaScriptEnabled = true

        changeButtonColor(R.color.blue_blue)
        binding.searchButton.setOnClickListener {
            val url = editText.text.toString()
                webview.loadUrl(url)

        }
    }
    private fun updateTitle(webView: WebView) {
        val pageTitle = webView.title
        if (pageTitle.isNullOrEmpty() || isErrorOccurred) {
            title = "Browser app"
        } else {
            title = pageTitle
        }
        isErrorOccurred = false
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true //is diplayed
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.blue_brown -> {
                changeButtonColor(R.color.blue_brown)
                return true
            }
            R.id.green_yellow -> {
                changeButtonColor(R.color.green_yellow)
                return true
            }
            R.id.violet_orange -> {
                changeButtonColor(R.color.violet_orange)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun changeButtonColor(colorResId: Int) {
        val color = ContextCompat.getColor(this, colorResId)
        binding.searchButton.setBackgroundColor(color)
    }

    override fun onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack()
        }
        else{
            super.onBackPressed()
        }
    }
}

