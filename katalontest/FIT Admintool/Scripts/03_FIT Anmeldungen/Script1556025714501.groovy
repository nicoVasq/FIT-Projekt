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

WebUI.setText(findTestObject('FIT anmeldungen/input_E-Mail_email'), 'theswush1029@gmail.com')

WebUI.setEncryptedText(findTestObject('FIT anmeldungen/input_Password_password'), '5xx1bkCcAlw=')

WebUI.click(findTestObject('Object Repository/button_Einloggen'))

WebUI.click(findTestObject('FIT anmeldungen/div_FIT Anmeldungen fr FIT am 23042019'))

WebUI.delay(1)

WebUI.click(findTestObject('FIT anmeldungen/i_Ausstehend_fa fa-pencil fa-1-5x'))

WebUI.click(findTestObject('FIT anmeldungen/button_Annehmen'))

WebUI.click(findTestObject('FIT anmeldungen/a_FIT'))

WebUI.click(findTestObject('FIT anmeldungen/a_FIT Anmeldungen'))

WebUI.delay(1)

WebUI.click(findTestObject('FIT anmeldungen/i_Ausstehend_fa fa-pencil fa-1-5x_1'))

WebUI.click(findTestObject('FIT anmeldungen/button_Ablehnen'))

WebUI.click(findTestObject('FIT anmeldungen/button_Ablehenen'))

WebUI.closeBrowser()

