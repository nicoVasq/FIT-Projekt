import { Helper } from './app.po';
import { browser, by, element, protractor } from 'protractor';

describe('log-file-explorer-client App', function () {

  let token: string = '4748-1169-2F3D';
  // let token: string = 'Firm-enTo-ken1';
  let parts: string[] = token.split('-');


  beforeAll(() => {
    browser.manage().window().maximize();
  });


  it('Should post booking', async () => {
    Helper.navigateTo('/');

    // Step 1
    element(by.id('code-part1')).sendKeys(parts[0]);
    element(by.id('code-part2')).sendKeys(parts[1]);
    element(by.id('code-part3')).sendKeys(parts[2]);

    // await
    Helper.pressButton('btnFitRegister');
    browser.waitForAngular();

    /*
    await browser.executeScript('window.scrollTo(0,700);').then(function () {

    });
    */
    Helper.pressButton('btnNext');
    browser.waitForAngular();
    await browser.executeScript('window.scrollTo(0,0);');

    // Step 2
    element(by.id('phoneNumber')).sendKeys('0660 5791261');
    element(by.id('email')).sendKeys('n.vas2000@outlook.com');
    element(by.id('homepage')).sendKeys('webpage.at');
    element(by.id('branch')).sendKeys('Automobil');
    element(by.id('description')).sendKeys('Eine kleine nette Firma :)');
    await browser.executeScript('window.scrollTo(0,700);');

    element(by.id('establishmentsAut')).element(by.css('.ng2-tag-input__text-input')).click();
    element(by.id('establishmentsAut')).element(by.css('.ng2-tag-input__text-input')).sendKeys('Wien');
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    element(by.id('establishmentsAut')).element(by.css('.ng2-tag-input__text-input')).sendKeys('Linz');
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    element(by.id('establishmentsAut')).element(by.css('.ng2-tag-input__text-input')).sendKeys('Graz');
    browser.actions().sendKeys(protractor.Key.ENTER).perform();

    element(by.id('establishmentsInt')).element(by.css('.ng2-tag-input__text-input')).click();
    element(by.id('establishmentsInt')).element(by.css('.ng2-tag-input__text-input')).sendKeys('London');
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    element(by.id('establishmentsInt')).element(by.css('.ng2-tag-input__text-input')).sendKeys('New York');
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await browser.executeScript('window.scrollTo(0,1000);');

    element(by.id('checkboxBranch0')).element(by.css('.cr')).click();
    element(by.id('checkboxBranch2')).element(by.css('.cr')).click();

    element(by.id('providesSummerJob')).element(by.css('.cr')).click();
    element(by.id('providesThesis')).element(by.css('.cr')).click();


    // Step 3
    Helper.pressButton('btnNext');
    browser.waitForAngular();
    await browser.executeScript('window.scrollTo(0,0);');

    element(by.id('representativeName0')).sendKeys('oofinger');
    element(by.id('representativeEmail0')).sendKeys('oof@gmail.com');

    element(by.id('btnAddRepresentative')).click();

    element(by.id('representativeName1')).sendKeys('Max Mustermann');
    element(by.id('representativeEmail1')).sendKeys('max.mustermann@gmail.com');

    await browser.executeScript('window.scrollTo(0,500);');
    element.all(by.css('.cr')).first().click();
    element(by.id('otherNotes')).sendKeys('Eigener Stand!');


    // Step 4
    // await
    Helper.pressButton('btnNext');
    await browser.executeScript('window.scrollTo(0,0);');

    element(by.id('lecturePack')).click();
    // element(by.id('btnLocationModal')).click();
    // element.all(by.css('.fit-location')).first().click();
    // element(by.id('btnSaveLocation')).click();
    // browser.sleep(1000);

    await browser.executeScript('window.scrollTo(0,810);');

    element(by.id('title')).sendKeys('Wie funktioniert Angular?');
    element(by.id('otherNotes')).sendKeys('Spoiler: gar nicht');
    element(by.id('checkboxBranch0')).element(by.css('.cr')).click();

    browser.sleep(3000);
    // browser.actions().mouseMove(element(by.id('btnNext'))).click().perform();
    // element(by.id('btnNext')).click();
    Helper.pressButton('btnNext');
    browser.waitForAngular();
    browser.sleep(1000);


    // Step 5
    element(by.id('fitContactFirstName')).clear();
    element(by.id('fitContactLastName')).clear();
    element(by.id('fitContactEmail')).clear();
    element(by.id('fitContactPhoneNumber')).clear();

    element(by.id('genderLabelF')).click();
    element(by.id('fitContactFirstName')).sendKeys('Maxine');
    element(by.id('fitContactLastName')).sendKeys('Musterfrau');
    element(by.id('fitContactEmail')).sendKeys('musterfrau@mail.com');
    element(by.id('fitContactPhoneNumber')).sendKeys('0660 5751731');
    element(by.id('remarks')).sendKeys('Erstellt mit Protractor :*');

    await browser.executeScript('window.scrollTo(0,700);');

    element(by.css('.cr')).click();
    element(by.id('btnSubmitBooking')).click();
    browser.sleep(1000);
    element(by.className('ajs-button ajs-ok')).click();

    browser.waitForAngular();

    expect(await element(by.id('submissionSuccessContainer')).isPresent()).toBeTruthy();
  });

});
