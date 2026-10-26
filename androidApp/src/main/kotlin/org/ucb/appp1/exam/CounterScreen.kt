package org.ucb.appp1.exam

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener

/** E25-4 */
@Composable
fun CounterScreen() {
    val context = LocalContext.current
    val ref = remember { FirebaseDatabase.getInstance().getReference("contador/valor") }
    var value by remember { mutableStateOf<Long?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        var last: Long? = null
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newValue = snapshot.getValue(Long::class.javaObjectType) ?: 0L
                if (last != null && last != newValue) {
                    Toast.makeText(context, "Nuevo valor: $newValue", Toast.LENGTH_SHORT).show()
                }
                last = newValue
                value = newValue
            }

            override fun onCancelled(e: DatabaseError) {
                error = e.message
            }
        }
        ref.addValueEventListener(listener)
        onDispose { ref.removeEventListener(listener) }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Contador global")
        Text(value?.toString() ?: "...")
        Button(onClick = {
            ref.runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val current = currentData.getValue(Long::class.javaObjectType) ?: 0L
                    currentData.value = current + 1
                    return Transaction.success(currentData)
                }

                override fun onComplete(e: DatabaseError?, committed: Boolean, snapshot: DataSnapshot?) {
                    if (e != null) error = e.message
                }
            })
        }) { Text("+1") }
        error?.let { Text("Error: $it") }
    }
}
