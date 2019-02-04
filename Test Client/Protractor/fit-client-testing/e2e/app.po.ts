import { browser, element, by } from 'protractor';

export class Helper {
  static navigateTo(url: string) {
    return browser.get(url);
  }
  static pressButton(bId: string ) {
    /*
    ' + element(by.js('return document.body.scrollHeight')) +
    browser.executeScript('window.scrollTo(0, 1100);').then(function () {
    element(by.id(bId)).click();
    });
    browser.executeScript(scrollToScript).then(function () {
      element(by.id(bId)).click();
    });
    */
     // browser.actions().mouseMove(element(by.id(bId))).click();

    browser.actions().mouseMove(element(by.id(bId))).perform().then(function () {
      browser.waitForAngular();
      element(by.id(bId)).click();
     });
  }
}
