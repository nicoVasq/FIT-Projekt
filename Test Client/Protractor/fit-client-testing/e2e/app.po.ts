import { browser, element, by } from 'protractor';

export class Helper {
  static navigateTo(url: string) {
    return browser.get(url);
  }
  static pressButton(bId: string) {
    browser.sleep(2000);
    return element(by.id(bId)).click();
  }
}
