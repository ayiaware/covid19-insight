package commitware.ayia.covid19.interfaces;


import commitware.ayia.covid19.models.News;

public interface OnFragmentListenerMain {
    void getListIntent(String intent, String argument);
    void getNewsIntent(News news);
    void getCallHelplineIntent(String helpline,String intent);
}
