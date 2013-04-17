package com.ulewo;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.HttpException;

import android.content.Context;
import android.widget.Toast;

public class AppException extends Exception {
	public final static byte TYPE_NETWORK = 0x01;
	public final static byte TYPE_SOCKET = 0x02;
	public final static byte TYPE_HTTP_ERROR = 0x04;
	public final static byte TYPE_JSON = 0x05;
	public final static byte TYPE_IO = 0x06;
	public final static byte TYPE_RUN = 0x07;
	private byte type;

	private AppException(byte type, Exception excp) {
		super(excp);
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	/**
	 * 提示友好的错误信息
	 * 
	 * @param ctx
	 */
	public void makeToast(Context ctx) {
		switch (this.getType()) {
		case TYPE_HTTP_ERROR:
			Toast.makeText(ctx, R.string.http_exception_error,
					Toast.LENGTH_SHORT).show();
			break;
		case TYPE_SOCKET:
			Toast.makeText(ctx, R.string.socket_exception_error,
					Toast.LENGTH_SHORT).show();
			break;
		case TYPE_NETWORK:
			Toast.makeText(ctx, R.string.network_not_connected,
					Toast.LENGTH_SHORT).show();
			break;
		case TYPE_JSON:
			Toast.makeText(ctx, R.string.josn_parser_failed, Toast.LENGTH_SHORT)
					.show();
			break;
		case TYPE_IO:
			Toast.makeText(ctx, R.string.io_exception_error, Toast.LENGTH_SHORT)
					.show();
			break;
		case TYPE_RUN:
			Toast.makeText(ctx, R.string.app_run_code_error, Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	/**
	 * 网络异常，请求超时
	 * 
	 * @param e
	 * @return
	 */
	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, e);
	}

	/**
	 * 网络异常，读取数据超时
	 * 
	 * @param e
	 * @return
	 */
	public static AppException socket(Exception e) {
		return new AppException(TYPE_SOCKET, e);
	}

	/**
	 * io异常
	 * 
	 * @param e
	 * @return
	 */
	public static AppException io(Exception e) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(TYPE_NETWORK, e);
		} else if (e instanceof IOException) {
			return new AppException(TYPE_IO, e);
		}
		return run(e);
	}

	public static AppException josn(Exception e) {
		return new AppException(TYPE_JSON, e);
	}

	/**
	 * 网络连接异常
	 * 
	 * @param e
	 * @return
	 */
	public static AppException network(Exception e) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(TYPE_NETWORK, e);
		} else if (e instanceof HttpException) {
			return http(e);
		} else if (e instanceof SocketException) {
			return socket(e);
		}
		return http(e);
	}

	/**
	 * 运行时异常
	 * 
	 * @param e
	 * @return
	 */
	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, e);
	}

}
