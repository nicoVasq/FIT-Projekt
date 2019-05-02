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

WebUI.setText(findTestObject('FIT bearbeiten/input_E-Mail_email'), 'theswush1029@gmail.com')

WebUI.setEncryptedText(findTestObject('FIT bearbeiten/input_Password_password'), '5xx1bkCcAlw=')

WebUI.click(findTestObject('FIT bearbeiten/button_Einloggen'))

WebUI.click(findTestObject('Object Repository/FIT bearbeiten/button_Anderen FIT auswhlen'))

WebUI.click(findTestObject('Object Repository/FIT bearbeiten/div_1 April 2020 Anmeldung von 1 Mrz 2020 bis 30 Mrz 2020'))

WebUI.click(findTestObject('FIT bearbeiten/div_FIT bearbeiten fr FIT am 09042019'))

WebUI.click(findTestObject('FIT bearbeiten/button_Neues Gescho hinzufgen'))

WebUI.delay(1)

WebUI.click(findTestObject('FIT bearbeiten/div_Zum Bearbeiten klicken_img-overlay'))

WebUI.delay(1)

WebUI.setText(findTestObject('FIT bearbeiten/input_Bezeichnung_areaDesigantion'), 'Obergeschoss')

WebUI.click(findTestObject('FIT bearbeiten/button_Neuen Platz hinzufgen'))

WebUI.delay(2)

WebUI.setText(findTestObject('FIT bearbeiten/input_Ausgewhlter Platz_form-control location-number-input text-center d-inline text-bold ng-untouched ng-valid ng-dirty'), 
    '245')

WebUI.click(findTestObject('FIT bearbeiten/button_Speichern_Etage'))

WebUI.delay(1)

WebUI.click(findTestObject('FIT bearbeiten/button_Speichern_FIT'))

WebUI.closeBrowser()

