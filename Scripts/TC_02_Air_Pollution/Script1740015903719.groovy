import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder as JsonBuilder
import groovy.json.JsonOutput as JsonOutput
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable


RequestObject testObject = findTestObject('Object Repository/GET_Air_Pollution')

ResponseObject objectResp = WS.sendRequestAndVerify(testObject, FailureHandling.STOP_ON_FAILURE)

def validateResponseAirPollution(ResponseObject objectResp) {
	
	KeywordUtil.logInfo('HEADER\n'+objectResp.getHeaderFields()+'\n\nBODY\n'+objectResp.getResponseBodyContent())
	JsonSlurper slurperJson = new JsonSlurper()
	def jsonResp = slurperJson.parseText(objectResp.getResponseText())
	
	assert jsonResp.coord.lat.toString().startsWith("-6.2") : 
		"Expected latitude: -6.2146, but got ${jsonResp.coord.lat}"
	assert jsonResp.coord.lon.toString().startsWith("106.") : 
		"Expected longitude: 106.8451, but got ${jsonResp.coord.lon}"
	assert jsonResp.list[0].main.aqi == 5 : 
		"Expected Air Quality Index: 5, but got ${jsonResp.list[0].main.aqi}"
	
	KeywordUtil.logInfo('Latitude: ' + jsonResp.coord.lat)
	KeywordUtil.logInfo('Longitude' + jsonResp.coord.lon)
	KeywordUtil.logInfo('Air Quality Index: ' + jsonResp.list[0].main.aqi)
	
}

validateResponseAirPollution(objectResp)