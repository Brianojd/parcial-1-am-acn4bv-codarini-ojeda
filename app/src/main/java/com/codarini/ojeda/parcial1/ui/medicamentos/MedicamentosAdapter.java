package com.codarini.ojeda.parcial1.ui.medicamentos;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.codarini.ojeda.parcial1.R;
import com.codarini.ojeda.parcial1.model.MedicamentoItem;
import java.util.ArrayList;
import java.util.List;
public class MedicamentosAdapter extends RecyclerView.Adapter<MedicamentosAdapter.VH> {

    private final Context context;
    private List<MedicamentoItem> items = new ArrayList<>();

    public MedicamentosAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<MedicamentoItem> nuevos) {
        this.items = (nuevos != null) ? nuevos : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_medicamento_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        MedicamentoItem it = items.get(pos);

        h.tvNombre.setText(it.nombre);

        String meta = it.construirMeta();
        if (meta == null || meta.isEmpty()) {
            h.tvMeta.setVisibility(View.GONE);
        } else {
            h.tvMeta.setVisibility(View.VISIBLE);
            h.tvMeta.setText(meta);
        }

        if (it.instrucciones == null || it.instrucciones.isEmpty()) {
            h.tvInstr.setVisibility(View.GONE);
        } else {
            h.tvInstr.setVisibility(View.VISIBLE);
            h.tvInstr.setText(it.instrucciones);
        }

        h.tvHora.setText(it.hora != null ? it.hora : "");

        // evento mínimo
        h.itemView.setOnClickListener(v ->
                Toast.makeText(context, "Medicamento: " + it.nombre, Toast.LENGTH_SHORT).show());
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMeta, tvInstr, tvHora;
        VH(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvMeta   = itemView.findViewById(R.id.tv_meta);
            tvInstr  = itemView.findViewById(R.id.tv_instr);
            tvHora   = itemView.findViewById(R.id.tv_hora);
        }
    }
}