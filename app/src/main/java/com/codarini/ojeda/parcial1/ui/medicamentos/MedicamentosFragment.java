package com.codarini.ojeda.parcial1.ui.medicamentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codarini.ojeda.parcial1.R;
import com.codarini.ojeda.parcial1.viewmodel.MedicamentosViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MedicamentosFragment extends Fragment  implements  MedicamentosActions{

    private MedicamentosViewModel vm;
    private TextView tvEmpty;

    private MedicamentosAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medicamentos, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rv_medicamentos);
        TextView tvEmpty = view.findViewById(R.id.tv_empty);



        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MedicamentosAdapter(requireContext());
        rv.setAdapter(adapter);

        vm = new ViewModelProvider(this).get(MedicamentosViewModel.class);

        vm.medicamentos.observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);

            boolean empty = (items == null || items.isEmpty());
            tvEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
            rv.setVisibility(empty ? View.GONE : View.VISIBLE);
        });

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        vm.escucharPendientesDeHoy(uid);
    }

    @Override
    public void onAgregarMedicamento() {

      new AddMedicamentoBottomSheet().show(getParentFragmentManager(),"addMe");
    }
}