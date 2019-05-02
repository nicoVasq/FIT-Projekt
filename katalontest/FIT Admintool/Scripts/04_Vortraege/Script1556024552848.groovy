import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:4200/admin-tool/login')

WebUI.setText(findTestObject('Object Repository/Vortraege/input_E-Mail_email'), 'theswush1029@gmail.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Vortraege/input_Password_password'), '5xx1bkCcAlw=')

WebUI.click(findTestObject('Object Repository/Vortraege/button_Einloggen'))

WebUI.click(findTestObject('Vortraege/div_Vortrge'))

WebUI.click(findTestObject('Object Repository/Vortraege/a_Offene Vortrge 100'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/Vortraege/button_Besttigen'))

WebUI.click(findTestObject('Vortraege/button_Besttigen_Accept'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/Vortraege/button_Ablehnen'))

WebUI.click(findTestObject('Vortraege/button_Ablehnen_Accept'))

WebUI.click(findTestObject('Object Repository/Vortraege/button_Gesperrt'))

WebUI.click(findTestObject('Object Repository/Vortraege/button_Sperren'))

WebUI.click(findTestObject('Object Repository/Vortraege/button_Offen'))

