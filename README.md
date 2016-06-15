# AdME

**AdME** stands for **Advertising Micro Edition**. It is a mobile Java ME API that allows developers to integrate ad networks into their apps, in order to make some revenues. AdME has a very extensible design, which allows it to work with most ad network.

## Articles

* [AdME: Ad Library for Java ME](http://j2megroup.blogspot.com.br/2010/12/adme-ad-library-for-java-me.html)

## Sample Code

```java
...

AdHandler adHandler = new InneractiveAdHandler("<app id>");
AdManager adManager = new AdManager(adHandler);
//
adManager.setAdListener(new AdListener() {
	public void onReceived(Ad ad) {
    	Image adImage = Image.createImage(ad.getImage());
    }
    
    public void onFailedAd(Throwable exception) {
    	System.out.println(exception.getMessage());
    }
});
//
adManager.requestAd();
...
```

## External Links

* [eMob Tech](http://www.emobtech.com)
* [J2ME Group Blog](http://j2megroup.blogspot.com/)
