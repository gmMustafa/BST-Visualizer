package com.gmMustafa.bst_visualizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.Boolean as Boolean

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var input: EditText? = null

    private var treevalIo: TextView? = null
    private var treevalPo: TextView? = null
    private var treevalPr: TextView? = null

    private var treeIo: String? = ""
    private var treePo: String? = ""
    private var treePr: String? = ""

    private var add: Button? = null
    private var del: Button? = null
    private var find: Button? = null
    private var reset: Button? = null
    private var rootMain: Node? = null
    private var flagFound: Boolean? = false
    private var count: Int = 0

    //Node
    class Node(var key: Int?, var left: Node?, var right: Node?)

    private fun insert(rootNode: Node?, nodeToInsert: Node?) {
        val r: Node? = rootNode
        if (r != null)
            if (r.key!! < nodeToInsert!!.key!!) {
                if (r.right == null)
                    r.right = nodeToInsert
                else
                    insert(r.right!!, nodeToInsert)

            } else if (r.left == null) {
                r.left = nodeToInsert

            } else {
                insert(r.left!!, nodeToInsert)

            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add = findViewById(R.id.btnAdd)
        add!!.setOnClickListener(this)

        del = findViewById(R.id.btnDel)
        del!!.setOnClickListener(this)

        find = findViewById(R.id.btnFind)
        find!!.setOnClickListener(this)

        reset = findViewById(R.id.btnReset)
        reset!!.setOnClickListener(this)

        input = findViewById(R.id.txtInput1)

        treevalIo = findViewById(R.id.treeValsIo)
        treevalPo = findViewById(R.id.treeValspo)
        treevalPr = findViewById(R.id.treeValspr)

    }


    private fun inOrder(root: Node?) {
        if (root != null) {
            inOrder(root = root.left)
            treeIo += """${root.key.toString()} """
            inOrder(root = root.right)
        }
    }

    private fun postOrder(root: Node?) {
        if (root != null) {
            postOrder(root = root.left)
            postOrder(root = root.right)
            treePo += """${root.key.toString()} """
        }
    }


    private fun preOrder(root: Node?) {
        if (root != null) {
            treePr += """${root.key.toString()} """
            preOrder(root = root.left)
            preOrder(root = root.right)
        }
    }


    private fun printTree() {
        inOrder(rootMain)
        treevalIo!!.text = treeIo
        treeIo = ""

        postOrder(rootMain)
        treevalPo!!.text = treePo
        treePo = ""


        preOrder(rootMain)
        treevalPr!!.text = treePr
        treePr = ""

    }


    private fun search(rootNode: Node?, key: Int): Node? {
        if (rootNode != null) {
            if (rootNode.key == key) {
                flagFound = true
                return rootNode
            }
            if (rootNode.key!! > key) {
                search(rootNode.left, key)
            }

            if (rootNode.key!! < key) {
                search(rootNode.right, key)
            }
        }
        return null
    }


    private fun deleteElement(rootNode: Node?, key: Int): Node? {
        val extraNode: Node? = rootNode
        if (extraNode == null) {
            return null
        } else {
            when {
                key < extraNode.key!! -> {
                    extraNode.left = deleteElement(extraNode.left, key)
                }
                key > rootNode.key!! -> {
                    extraNode.right = deleteElement(extraNode.right, key)
                }
                else -> {
                    if (extraNode.left == null) {
                        return extraNode.right
                    } else if (extraNode.right == null) {
                        return extraNode.left
                    }

                    extraNode.key = miniminValue(extraNode.right!!)
                    extraNode.right = deleteElement(extraNode.right, extraNode.key!!)

                }
            }
            return extraNode
        }
    }

    private fun miniminValue(node: Node): Int? {
        var currentNode: Node? = node
        var minVal: Int? = currentNode!!.key
        while (currentNode!!.left != null) {
            minVal = currentNode.left!!.key
            currentNode = currentNode.left!!
        }
        return minVal
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnAdd -> {
                val input: Int
                if (txtInput1.text.isEmpty()) {
                    txtInput1.error = "Enter Value"
                } else {
                    input = txtInput1.text.toString().toInt()
                    if (count == 0) {
                        count += 1
                        rootMain = Node(input, null, null)
                        Toast.makeText(
                            this.applicationContext,
                            "Root Inserted",
                            Toast.LENGTH_SHORT
                        ).show()
                        insert(null, rootMain)
                    } else {
                        val ins = Node(input, null, null)
                        insert(rootMain, ins)
                        Toast.makeText(
                            this.applicationContext,
                            "Element Inserted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    printTree()
                    txtInput1.setText("")
                }
            }

            R.id.btnDel -> {
                if (findValue()) {
                    val input: Int = txtInput1.text.toString().toInt()
                    deleteElement(rootMain, input)
                    Toast.makeText(
                        this.applicationContext,
                        "Element Deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    printTree()
                }
            }

            R.id.btnFind -> {
                findValue()
            }

            R.id.btnReset -> {
                count = 0
                txtInput1.setText("")
                treeIo = ""
                treePr = ""
                treePo = ""
                treevalIo!!.text = treeIo
                treevalPo!!.text = treePo
                treevalPr!!.text = treePr
                rootMain = null
            }

        }
    }


    private fun findValue(): Boolean {
        flagFound = false
        val input: Int
        if (txtInput1.text.isEmpty()) {
            txtInput1.error = "Enter Value"
        } else {
            input = txtInput1.text.toString().toInt()
            search(rootMain, input)
            if (flagFound as Boolean) {
                Toast.makeText(
                    this.applicationContext,
                    "$input Value Found",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this.applicationContext,
                    "$input Value Not Found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return flagFound as Boolean
    }

}