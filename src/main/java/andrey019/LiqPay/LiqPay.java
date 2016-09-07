package andrey019.LiqPay;


import org.json.simple.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;


public class LiqPay implements LiqPayApi {

    private final String publicKey;
    private final String privateKey;
    private final String callbackUrl;
    private boolean cnbSandbox = true;
    private boolean renderPayButton;


    public LiqPay(String publicKey, String privateKey, String callbackUrl) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.callbackUrl = callbackUrl;
        checkRequired();
    }

    private void checkRequired() {
        if (this.publicKey == null || this.publicKey.isEmpty()) {
            throw new IllegalArgumentException("publicKey is empty");
        }
        if (this.privateKey == null || this.privateKey.isEmpty()) {
            throw new IllegalArgumentException("privateKey is empty");
        }
        if (this.callbackUrl == null || this.callbackUrl.isEmpty()) {
            throw new IllegalArgumentException("callbackUrl is empty");
        }
    }

    public boolean isCnbSandbox() {
        return cnbSandbox;
    }

    public void setCnbSandbox(boolean cnbSandbox) {
        this.cnbSandbox = cnbSandbox;
    }

    public boolean isRenderPayButton() {
        return renderPayButton;
    }

    public void setRenderPayButton(boolean renderPayButton) {
        this.renderPayButton = renderPayButton;
    }

    protected Map<String, String> withBasicApiParams(Map<String, String> params) {
        params.put("public_key", publicKey);
        params.put("version", API_VERSION);
        return params;
    }

    protected Map<String, String> withSandboxParam(Map<String, String> params) {
        if (params.get("sandbox") == null && isCnbSandbox()) {
            params.put("sandbox", "1");
            return params;
        }
        return params;
    }

    @Override
    public String cnb_form(Map<String, String> params) {
        checkCnbParams(params);
        String data = base64_encode(JSONObject.toJSONString(withSandboxParam(withBasicApiParams(params))));
        String signature = createSignature(data);
        String language = params.get("language") != null ? params.get("language") : DEFAULT_LANG;
        return renderHtmlForm(data, language, signature);
    }

    private String renderHtmlForm(String data, String language, String signature) {
        StringBuilder form = new StringBuilder();
        form.append("<form id=\"donateForm\" method=\"post\" action=\"");
        form.append(LIQPAY_API_CHECKOUT_URL);
        form.append("\" accept-charset=\"utf-8\">\n");
        form.append("<input type=\"hidden\" name=\"data\" value=\"");
        form.append(data);
        form.append("\" />\n");
        form.append("<input type=\"hidden\" name=\"signature\" value=\"");
        form.append(signature);
        form.append("\" />\n");
        if (this.renderPayButton) {
            form.append("<input id=\"donateButton\" type=\"image\" src=\"https://static.liqpay.com/buttons/p1");
            form.append(language);
            form.append(".radius.png\" name=\"btn_text\" />\n");
        }
        form.append("</form>\n");
        return form.toString();
    }

    protected void checkCnbParams(Map<String, String> params) {
        if (params.get("amount") == null)
            throw new NullPointerException("amount can't be null");
        if (params.get("currency") == null)
            throw new NullPointerException("currency can't be null");
        if (params.get("description") == null)
            throw new NullPointerException("description can't be null");
    }

    public String str_to_sign(String str) {
        return base64_encode(sha1(str));
    }

    protected String createSignature(String base64EncodedData) {
        return str_to_sign(privateKey + base64EncodedData + privateKey);
    }

    @Override
    public boolean checkValidity(String data, String signature) {
        if ( (data == null) || (signature == null) ) {
            return false;
        }
        String sign = base64_decode(signature);
        String signCheck = new String(sha1(privateKey + data + privateKey));
        return sign.equals(signCheck);
    }

    @Override
    public String generateDonationForm(String orderId, double amount) {
        Map params = new HashMap();
        params.put("action", "pay");
        params.put("amount", Double.toString(amount));
        params.put("currency", "UAH");
        params.put("description", "donation");
        params.put("order_id", orderId);
        params.put("server_url", callbackUrl);
        return cnb_form(params);
    }

    public byte[] sha1(String param) {
        try {
            MessageDigest SHA = MessageDigest.getInstance("SHA-1");
            SHA.reset();
            SHA.update(param.getBytes("UTF-8"));
            return SHA.digest();
        } catch (Exception e) {
            throw new RuntimeException("Can't calc SHA-1 hash", e);
        }
    }

    public String base64_encode(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public String base64_encode(String data) {
        return base64_encode(data.getBytes());
    }

    @Override
    public String base64_decode(String data) {
        return new String(DatatypeConverter.parseBase64Binary(data));
    }
}