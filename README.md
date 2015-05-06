# Food-Manager

# Barcode to Product
To scan a Barcode you have to do the following:
####Call
`new IntentIntegrator(this).initiateScan();`

####Callback
```
public void onActivityResult(int requestCode, int resultCode, Intent intent)
{
 IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
 if(result != null)
 {
   Product product = BarcodeToProductConverter.getProductForBarcode(result.getContents());
 }
 else
 {
   //no product found
 }
}
```
