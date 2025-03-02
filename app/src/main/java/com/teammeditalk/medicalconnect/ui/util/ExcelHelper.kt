package com.teammeditalk.medicalconnect.ui.util

import android.content.Context
import com.teammeditalk.medicalconnect.data.model.foreign.LangAvailablePharmacy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import timber.log.Timber

class ExcelHelper(
    private val context: Context,
) {
    suspend fun readForeignLanguageList(): List<LangAvailablePharmacy> =
        withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open("foreignLanguageAvailableList.xlsx")

                val workbook = XSSFWorkbook(inputStream)
                val sheet = workbook.getSheetAt(0)

                val items = mutableListOf<LangAvailablePharmacy>()

                for (rowIndex in 5..sheet.lastRowNum) {
                    val row = sheet.getRow(rowIndex) ?: continue

                    val availableList = mutableListOf<String>()
                    val english = row.getCell(5)?.stringCellValue
                    val chinese = row.getCell(6)?.stringCellValue
                    val japanese = row.getCell(7)?.stringCellValue
                    val option = row.getCell(8)?.stringCellValue

                    if (english == "○") {
                        availableList.add("English")
                    }
                    if (chinese == "○") {
                        availableList.add("Chinese")
                    }
                    if (japanese == "○") {
                        availableList.add("Japanese")
                    }
                    if (option != null) {
                        availableList.add(option.toString())
                    }
                    val item =
                        LangAvailablePharmacy(
                            phone = row.getCell(4)?.stringCellValue ?: "",
                            region = row.getCell(1)?.stringCellValue ?: "",
                            pharmacyName = row.getCell(2)?.stringCellValue ?: "",
                            address = row.getCell(3)?.stringCellValue ?: "",
                            availableLanguageList = availableList,
                        )
                    items.add(item)
                }
                items
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.d("failed to load list :${e.message}")
                emptyList<LangAvailablePharmacy>()
            }
        }
}
