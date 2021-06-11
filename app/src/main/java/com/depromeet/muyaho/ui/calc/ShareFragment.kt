package com.depromeet.muyaho.ui.calc

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseFragment
import com.depromeet.muyaho.databinding.FragmentShareBinding
import com.depromeet.muyaho.util.NumberFormatUtil
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@AndroidEntryPoint
class ShareFragment : BaseFragment<FragmentShareBinding, ShareViewModel, ShareViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.fragment_share
    override val vm: ShareViewModel by viewModels()
    private val args: ShareFragmentArgs by navArgs()

    val budget: Long by lazy {
        args.budget
    }
    var benefitRate: Float = 0.0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sliderBenefitRate.addOnChangeListener { slider, value, fromUser ->
            benefitRate = value
            updateUI()
        }

        binding.clContent.buildDrawingCache()
        binding.clContent.isDrawingCacheEnabled = true
        binding.tvSaveImage.setOnClickListener {
            val saveBitmap = binding.clContent.getDrawingCache()
            saveMediaToStorage(saveBitmap)
        }

        benefitRate = args.benefitRate
        binding.sliderBenefitRate.value = (benefitRate.toInt() / 10 * 10).toFloat()
        updateUI()
        binding.chBenefitRate.text = "+${benefitRate.toString()}%"
    }

    fun updateUI() {
        val benefit = budget * ((benefitRate + 100) / 100)
        binding.tvFutureBudget.text = NumberFormatUtil.numWithComma(benefit.toBigDecimal())
        binding.chBenefitRate.text = "+${benefitRate.toString()}%"

        if (benefit < 200000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_default)
            binding.root.setBackgroundResource(R.color.primary_blue)
            binding.clContent.setBackgroundResource(R.color.primary_blue)
        } else if (benefit >= 200000 && benefit < 500000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_bicycle)
            binding.root.setBackgroundResource(R.color.primary_blue)
            binding.clContent.setBackgroundResource(R.color.primary_blue)
        } else if (benefit >= 500000 && benefit < 3000000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_hover)
            binding.root.setBackgroundResource(R.color.primary_blue)
            binding.clContent.setBackgroundResource(R.color.primary_blue)
        } else if (benefit >= 3000000 && benefit < 10000000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_scooter)
            binding.root.setBackgroundResource(R.color.primary_blue)
            binding.clContent.setBackgroundResource(R.color.primary_blue)
        } else if (benefit >= 10000000 && benefit < 20000000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_harley)
            binding.root.setBackgroundResource(R.color.primary_blue)
            binding.clContent.setBackgroundResource(R.color.primary_blue)
        } else if (benefit >= 20000000 && benefit < 50000000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_car1)
            binding.root.setBackgroundResource(R.color.primary_blue)
            binding.clContent.setBackgroundResource(R.color.primary_blue)
        } else if (benefit >= 50000000 && benefit < 100000000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_car2)
            binding.root.setBackgroundResource(R.color.primary_blue)
            binding.clContent.setBackgroundResource(R.color.primary_blue)
        } else if (benefit >= 100000000 && benefit < 300000000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_car3)
            binding.root.setBackgroundResource(R.color.black_1)
            binding.clContent.setBackgroundResource(R.color.black_1)
        } else if (benefit >= 300000000 && benefit < 500000000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_airplane)
            binding.root.setBackgroundResource(R.color.black_1)
            binding.clContent.setBackgroundResource(R.color.black_1)
        } else if (benefit >= 500000000) {
            binding.ivYoungchan.setImageResource(R.drawable.img_youngchan_rocket)
            binding.root.setBackgroundResource(R.color.black_1)
            binding.clContent.setBackgroundResource(R.color.black_1)
        }
    }

    fun saveMediaToStorage(bitmap: Bitmap) {
        //Generating a file name
        val filename = "Youngcha_${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            context?.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context, "Saved To Photos", Toast.LENGTH_SHORT).show()
        }
    }
}