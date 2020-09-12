package commitware.ayia.covid19.interfaces;

import android.view.View;

public interface RecyclerViewClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
