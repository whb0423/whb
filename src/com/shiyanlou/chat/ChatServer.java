package com.shiyanlou.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

/**
 * �����������
 * @author shiyanlou
 *
 */
@ServerEndpoint("/websocket")
public class ChatServer {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");    // ���ڸ�ʽ��

    @OnOpen
    public void open(Session session) {
        // ��ӳ�ʼ������
    }

    /**
     * ���ܿͻ��˵���Ϣ��������Ϣ���͸��������ӵĻỰ
     * @param message �ͻ��˷�������Ϣ
     * @param session �ͻ��˵ĻỰ
     */
    @OnMessage
    public void getMessage(String message, Session session) {
        // �ѿͻ��˵���Ϣ����ΪJSON����
        JSONObject jsonObject = JSONObject.fromObject(message);
        // ����Ϣ����ӷ�������
        jsonObject.put("date", DATE_FORMAT.format(new Date()));
        // ����Ϣ���͸��������ӵĻỰ
        for (Session openSession : session.getOpenSessions()) {
            // ��ӱ�����Ϣ�Ƿ�Ϊ��ǰ�Ự�����ı�־
            jsonObject.put("isSelf", openSession.equals(session));
            // ����JSON��ʽ����Ϣ
            openSession.getAsyncRemote().sendText(jsonObject.toString());
        }
    }

    @OnClose
    public void close() {
        // ��ӹرջỰʱ�Ĳ���
    }

    @OnError
    public void error(Throwable t) {
        // ��Ӵ������Ĳ���
    }
}