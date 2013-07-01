package weibo4j.examples.oauth2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

public class OAuth4Code {
	public static void main(String[] args) throws WeiboException, IOException {

		Oauth oauth = new Oauth();
		String client_id = "3552164515";
		String redirect_uri = "http://ulewo.com/login";
		BareBonesBrowserLaunch.openURL(oauth.authorize("code", client_id, redirect_uri));
		System.out.println(oauth.authorize("code", args[0], args[1]));
		System.out.print("Hit enter when it's done.[Enter]:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = br.readLine();
		Log.logInfo("code: " + code);
		try {
			System.out.println(oauth.getAccessTokenByCode(code));
		} catch (WeiboException e) {
			if (401 == e.getStatusCode()) {
				Log.logInfo("Unable to get the access token.");
			}
			else {
				e.printStackTrace();
			}
		}
	}

}
