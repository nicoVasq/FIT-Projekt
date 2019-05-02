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

WebUI.setText(findTestObject('Object Repository/FIT erstellen/input_E-Mail_email'), 'theswush1029@gmail.com')

WebUI.setEncryptedText(findTestObject('Object Repository/FIT erstellen/input_Password_password'), '5xx1bkCcAlw=')

WebUI.click(findTestObject('Object Repository/FIT erstellen/button_Einloggen'))

WebUI.click(findTestObject('Object Repository/FIT erstellen/div_Neuen FIT anlegen'))

WebUI.click(findTestObject('Object Repository/FIT erstellen/button_Neuen FIT erstellen'))

WebUI.setText(findTestObject('FIT erstellen/input_Der FIT findet statt am'), '01. April 2020')

WebUI.setText(findTestObject('FIT erstellen/input_Anmelde beginn'), '01. März 2020')

WebUI.setText(findTestObject('FIT erstellen/input_Anmelde ende'), '30. März 2020')

WebUI.click(findTestObject('Object Repository/FIT erstellen/div_Zum Bearbeiten klicken_img-overlay'))

WebUI.delay(1)

WebUI.setText(findTestObject('Object Repository/FIT erstellen/input_Bezeichnung_areaDesigantion'), 'Etage 1')

WebUI.click(findTestObject('Object Repository/FIT erstellen/button_Neuen Platz hinzufgen'))

WebUI.setText(findTestObject('Object Repository/FIT erstellen/input_Ausgewhlter Platz'), '2')

WebUI.click(findTestObject('Object Repository/FIT erstellen/button_Speichern'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/FIT erstellen/button_Anlegen'))

WebUI.delay(2)

WebUI.closeBrowser()

