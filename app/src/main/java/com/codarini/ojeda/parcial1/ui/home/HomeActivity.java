package com.codarini.ojeda.parcial1.ui.home;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.codarini.ojeda.parcial1.R;
import com.codarini.ojeda.parcial1.ui.medicamentos.MedicamentosAdapter;
import com.codarini.ojeda.parcial1.viewmodel.MedicamentosViewModel;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rv = findViewById(R.id.rv_medicamentos);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        MedicamentosAdapter adapter = new MedicamentosAdapter(this);
        rv.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_agregar);

        MedicamentosViewModel vm = new ViewModelProvider(this).get(MedicamentosViewModel.class);
        vm.medicamentos.observe(this, adapter::setItems);

        vm.cargarNoIniciadas(1L);

        fab.setOnClickListener(v -> vm.agregarMocks());
    }
}
