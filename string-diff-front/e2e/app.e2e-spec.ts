import { StringDiffFrontPage } from './app.po';

describe('string-diff-front App', () => {
  let page: StringDiffFrontPage;

  beforeEach(() => {
    page = new StringDiffFrontPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
