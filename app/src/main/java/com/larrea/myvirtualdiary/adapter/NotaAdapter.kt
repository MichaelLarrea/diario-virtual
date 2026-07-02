package com.larrea.myvirtualdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.larrea.myvirtualdiary.R
import com.larrea.myvirtualdiary.data.Nota

class NotaAdapter(
    private var listaNotas: List<Nota>,
    private val alTocarNota: (Nota) -> Unit,
    private val alMantenerNota: (Nota) -> Unit
) : RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    class NotaViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvTituloNota: TextView =
            itemView.findViewById(R.id.tvTituloNota)

        val tvFechaNota: TextView =
            itemView.findViewById(R.id.tvFechaNota)

        val tvContenidoNota: TextView =
            itemView.findViewById(R.id.tvContenidoNota)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotaViewHolder {

        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nota, parent, false)

        return NotaViewHolder(vista)
    }

    override fun onBindViewHolder(
        holder: NotaViewHolder,
        position: Int
    ) {
        val notaActual = listaNotas[position]

        holder.tvTituloNota.text = notaActual.titulo
        holder.tvFechaNota.text = notaActual.fecha
        holder.tvContenidoNota.text = notaActual.contenido

        holder.itemView.setOnClickListener {
            alTocarNota(notaActual)
        }

        holder.itemView.setOnLongClickListener {
            alMantenerNota(notaActual)
            true
        }
    }

    override fun getItemCount(): Int {
        return listaNotas.size
    }

    fun actualizarLista(nuevaLista: List<Nota>) {
        listaNotas = nuevaLista
        notifyDataSetChanged()
    }
}