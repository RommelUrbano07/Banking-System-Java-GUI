package encryptionqueue;
import encryptionqueue.CircularQueue;
import static encryptionqueue.EncryptionQueue.offset;
public class GUI extends javax.swing.JFrame {
    public GUI() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    EncryptionQueue var = new EncryptionQueue();
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        ENCRYPT_INPUT = new javax.swing.JTextField();
        DECRYPT_INPUT = new javax.swing.JTextField();
        ENCRYPT = new javax.swing.JButton();
        DECRYPT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ENCRYPT_OUTPUT = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        DECRYPT_OUTPUT = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        DECRYPT_INPUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DECRYPT_INPUTActionPerformed(evt);
            }
        });

        ENCRYPT.setText("ENCRYPT TEXT");
        ENCRYPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ENCRYPTActionPerformed(evt);
            }
        });

        DECRYPT.setText("DECRYPT TEXT");
        DECRYPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DECRYPTActionPerformed(evt);
            }
        });

        jLabel1.setText("ENCRYPTION AND DECRYPTION APP");

        ENCRYPT_OUTPUT.setEditable(false);
        ENCRYPT_OUTPUT.setColumns(20);
        ENCRYPT_OUTPUT.setLineWrap(true);
        ENCRYPT_OUTPUT.setRows(5);
        jScrollPane1.setViewportView(ENCRYPT_OUTPUT);

        DECRYPT_OUTPUT.setEditable(false);
        DECRYPT_OUTPUT.setColumns(20);
        DECRYPT_OUTPUT.setLineWrap(true);
        DECRYPT_OUTPUT.setRows(5);
        jScrollPane2.setViewportView(DECRYPT_OUTPUT);

        jButton1.setText("EXIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ENCRYPT)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DECRYPT_INPUT, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ENCRYPT_INPUT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2))))
                        .addContainerGap(47, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DECRYPT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(64, 64, 64))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(315, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(286, 286, 286))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(ENCRYPT_INPUT, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ENCRYPT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(DECRYPT_INPUT, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DECRYPT)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ENCRYPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ENCRYPTActionPerformed
        String input=ENCRYPT_INPUT.getText();
        CircularQueue queue= var.genCircle(4);
        ENCRYPT_OUTPUT.setText(var.encrypt(ENCRYPT_INPUT.getText()));
    }//GEN-LAST:event_ENCRYPTActionPerformed

    private void DECRYPT_INPUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DECRYPT_INPUTActionPerformed
       
    }//GEN-LAST:event_DECRYPT_INPUTActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void DECRYPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DECRYPTActionPerformed
        
        CircularQueue queue= var.genCircle(4);
        String output="";
        String arr=var.decrypt(DECRYPT_INPUT.getText());
        DECRYPT_OUTPUT.setText(arr);
    }//GEN-LAST:event_DECRYPTActionPerformed

    public void run() {
        /* Create and display the form */
        new GUI().setVisible(true);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DECRYPT;
    private javax.swing.JTextField DECRYPT_INPUT;
    private javax.swing.JTextArea DECRYPT_OUTPUT;
    private javax.swing.JButton ENCRYPT;
    private javax.swing.JTextField ENCRYPT_INPUT;
    private javax.swing.JTextArea ENCRYPT_OUTPUT;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
